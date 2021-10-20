package mappers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper
import org.joda.time.LocalTime
import com.github.nscala_time.time.Imports.*
import java.util.StringTokenizer
import com.typesafe.config._
import org.slf4j.{LoggerFactory, Logger}

/**
 * A mapper class for the MapReduce Job 1: Distribution of message types in logs with messages of a particular pattern across predefined time interval.
 */
class DistributionMapper extends Mapper[Object, Text, Text, IntWritable] {

  // Create logger
  val logger: Logger = LoggerFactory.getLogger(getClass)

  // Load custom configurations
  val config = ConfigFactory.load()
  val initialTime = config.getString("DistributionMessageType.initialTime")
  val finalTime = config.getString("DistributionMessageType.finalTime")
  val timeRegex = config.getString("DistributionMessageType.timeRegex")
  val messageTypeRegex = config.getString("DistributionMessageType.messageTypeRegex")
  val patternRegex = config.getString("DistributionMessageType.patternRegex")

  /**
   * A mapper method for the MapReduce Job 1. Takes a key value pair of logs as input where the String log content is value. Returns messageType: Text as key and IntWritable(1) as value.
   */
  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {

    // Create IntWritable (Integer datatype of hadoop) instance with value 1
    val one = new IntWritable(1)

    // Create Apache text object, store text using standard UTF8 encoding
    val word = new Text()

    // Create tokenizer that tokenizes log events delimiter "\n". Elements are in String datatype.
    val itr = new StringTokenizer(value.toString, "\n")

    // Create LocalTime instance for time frame's initial time
    val initial_time = LocalTime.parse(initialTime)

    // Create LocalTime instance for time frame's ending time
    val final_time = LocalTime.parse(finalTime)


    while (itr.hasMoreTokens()) {

      val str = itr.nextToken() // Single log event

      // Check if time pattern matches
      val timePattern = timeRegex.r // scala.util.matching.Regex instance with time pattern
      val option_string_match = timePattern.findFirstIn(str) // Finds first occurence of time pattern and returns Option[String]

      option_string_match match {
        case Some(s) => {

          val t = LocalTime.parse(s) // Time of the log event

          if ((t >= initial_time) && (t <= final_time)) { // Comparing time (inclusive)

            // Check if pattern exists
            val pattern = patternRegex.r // scala.util.matching.Regex instance with pattern
            val customPattern = pattern.findFirstIn(str) // Finds first occurence of pattern and returns Option[String]

            customPattern match {
              case Some(sp) => {

                // Check if messageType pattern exists
                val messageType = messageTypeRegex.r // scala.util.matching.Regex instance with message type pattern
                val matchMessageType = messageType.findFirstIn(str) // Finding message type

                matchMessageType match {
                  case Some(sm) => {

                    // Write output in context variable
                    word.set(sm) // Converting from Option[String] to Hadoop Text instance
                    context.write(word, one) // Key = MESSAGE TYPE, VALUE = 1
                    logger.info(s"A message found that matches with requirements. Output: ${word}, ${one}")
                  }
                  case None => // If log message type pattern is not found - Do nothing
                }

              }
              case None => // If pattern not found - Do nothing
            }

          }

        }

        case None => // If time pattern is not found - Do nothing
      }

    }
  }
}

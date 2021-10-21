package mappers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper
import org.joda.time.LocalTime
import com.github.nscala_time.time.Imports.*
import java.util.StringTokenizer
import com.typesafe.config._
import org.slf4j.{LoggerFactory, Logger}

/**
 * A mapper class for the MapReduce Job 2: Compute time intervals sorted in the descending order that contained most log messages of the type ERROR with injected regex pattern string instances.
 */
class TimeIntervalMapper extends Mapper[Object, Text, Text, IntWritable] {
  // Create logger
  val logger: Logger = LoggerFactory.getLogger(getClass)

  // Load custom configurations
  val config = ConfigFactory.load()
  val timeRegex = config.getString("DistributionMessageType.timeRegex")
  val messageTypeErrorRegex = config.getString("DistributionMessageType.messageTypeErrorRegex")
  val patternRegex = config.getString("DistributionMessageType.patternRegex")
  val timeInterval = config.getInt("DistributionMessageType.timeInterval")

  /**
   * A mapper method for the MapReduce Job 2. Takes a key value pair of logs as input where the String log content is value. Returns messageType: Text (Time Slot) as key and IntWritable(1) as value.
   */
  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {

    // Create IntWritable (Integer datatype of hadoop) instance with value 1
    val one = new IntWritable(1)

    // Create Apache text object, store text using standard UTF8 encoding
    val word = new Text()

    // Create tokenizer that tokenizes log events delimiter "\n". Elements are in String datatype.
    val itr = new StringTokenizer(value.toString, "\n")

    // Defining first intervals as None
    var initTime = LocalTime(00,00,00,000)
    var endTime = LocalTime(23,59,59,000)

    while (itr.hasMoreTokens()) {
      val str = itr.nextToken() // Single log event


      val timeReg = timeRegex.r // scala.util.matching.Regex instance with time pattern
      val logTime = timeReg.findFirstIn(str) // Finds first occurence of time pattern and returns Option[String]

      logTime match {
        case Some(t) => {
          val lTime = LocalTime.parse(t)

          // Defining first time intervals
          if(initTime == endTime.plusSeconds(1)){
            initTime = lTime
            endTime = initTime.plusMinutes(10)
          }

          if (lTime>endTime) {
            initTime = endTime.plusSeconds(1)
            endTime = initTime.plusMinutes(timeInterval)
          }

          // Check if pattern exists
          val pattern = patternRegex.r // scala.util.matching.Regex instance with pattern
          val customPattern = pattern.findFirstIn(str)// Finds first occurence of pattern and returns Option[String]

          customPattern match{
            case Some(sp) => {

              // Check if messageType pattern exists
              val messageType = messageTypeErrorRegex.r // scala.util.matching.Regex instance with message type pattern
              val matchMessageType = messageType.findFirstIn(str) // Finding message type

              matchMessageType match {
                case Some(sm) => {

                  // Write output in context variable
                  word.set(initTime.toString() + " - " + endTime.toString()) // Converting from Option[String] to Hadoop Text instance
                  context.write(word, one) // Key = TIME SLOT, VALUE = 1
                  logger.info(s"A message found that matches with requirements. Output: ${word}, ${one}")
                }
                case None => // If log message type pattern is not found - Do nothing

              }

            }
            case None => // If pattern not found - Do nothing
          }
        }
        case None => // Do nothing
      }

    }
  }
}

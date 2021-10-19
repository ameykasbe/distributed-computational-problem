package mappers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper
import org.joda.time.LocalTime
import com.github.nscala_time.time.Imports.*

import java.util.StringTokenizer
import com.typesafe.config._


class DistributionMapper extends Mapper[Object, Text, Text, IntWritable] {

  val config = ConfigFactory.load()
  val initialTime = config.getString("DistributionMessageType.initialTime")
  val finalTime = config.getString("DistributionMessageType.finalTime")
  val timeRegex = config.getString("DistributionMessageType.timeRegex")
  val messageTypeRegex = config.getString("DistributionMessageType.messageTypeRegex")
  val patternRegex = config.getString("DistributionMessageType.patternRegex")


  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
    val one = new IntWritable(1) // Integer datatype of hadoop
    val word = new Text() // Apache text object, store text using standard UTF8 encoding
    val itr = new StringTokenizer(value.toString, "\n") // Tokenizes using default delimiter set, which is " \t\n\r\f". Elements are in String
    val initial_time = LocalTime.parse(initialTime) // The time frame's initial time (inclusive)
    val final_time = LocalTime.parse(finalTime) // The time frame's ending time (inclusive)

    while (itr.hasMoreTokens()) {
      val str = itr.nextToken() // Single log event
      val pattern = timeRegex.r // scala.util.matching.Regex instance with time pattern
      val option_string_match = pattern.findFirstIn(str) // Finds first occurence of pattern and returns Option[String]
      option_string_match match {
        case Some(s) => {
          val t = LocalTime.parse(s) // Time of the log event
          if ((t >= initial_time) && (t <= final_time)) // Comparing time{
            val pattern2 = messageTypeRegex.r // scala.util.matching.Regex instance with message type pattern
            val match_message_type = pattern2.findFirstIn(str) // Finding message type
            match_message_type match {
              case Some(sm) => {
                word.set(sm) // Converting from Option[String] to Hadoop Text instance
                context.write(word, one) // Key = MESSAGE TYPE, VALUE = 1
              }
              case None => // If pattern is not found - Do nothing
            }
          }
        case None => // If pattern is not found - Do nothing
        }
      }
    }
  }

package mappers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper
import org.joda.time.LocalTime
import com.github.nscala_time.time.Imports.*

import java.util.StringTokenizer
import com.typesafe.config._


class MaxCharacterMapper extends Mapper[Object, Text, Text, IntWritable] {

  val config = ConfigFactory.load()
  val messageTypeRegex = config.getString("DistributionMessageType.messageTypeRegex")
  val patternRegex = config.getString("DistributionMessageType.patternRegex")


  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
    val one = new IntWritable(1) // Integer datatype of hadoop
    val word = new Text() // Apache text object, store text using standard UTF8 encoding
    val itr = new StringTokenizer(value.toString, "\n") // Tokenizes using default delimiter set, which is " \t\n\r\f". Elements are in String


    while (itr.hasMoreTokens()) {
      val str = itr.nextToken() // Single log event
      val pattern = patternRegex.r
      val customPattern = pattern.findFirstIn(str)

      customPattern match{
        case Some(sp) => {
          val messageType = messageTypeRegex.r // scala.util.matching.Regex instance with message type pattern
          val matchMessageType = messageType.findFirstIn(str) // Finding message type


          matchMessageType match {
            case Some(sm) => {
              word.set(sm) // Converting from Option[String] to Hadoop Text instance
              context.write(word, new IntWritable(str.split(" +").last.length())) // Key = MESSAGE TYPE, VALUE = Log message's length
            }
            case None => // If log message type pattern is not found - Do nothing
          }


        }
        case None => // If pattern not found - Do nothing
      }


    }
  }
}

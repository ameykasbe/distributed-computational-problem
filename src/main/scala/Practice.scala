import java.util.StringTokenizer
import scala.util.matching.Regex
import org.apache.hadoop.io.{IntWritable, Text}
import com.github.nscala_time.time.Imports.*
import org.joda.time.LocalTime
import com.typesafe.config._
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper
import org.joda.time.LocalTime
import com.github.nscala_time.time.Imports.*

import java.util.StringTokenizer
import com.typesafe.config._
import org.apache.hadoop.fs.Path
object Practice {

  val config = ConfigFactory.load()
  val initialTime = config.getString("DistributionMessageType.initialTime")
  val finalTime = config.getString("DistributionMessageType.finalTime")
  val timeRegex = config.getString("DistributionMessageType.timeRegex")
  val messageTypeRegex = config.getString("DistributionMessageType.messageTypeRegex")
  val patternRegex = config.getString("DistributionMessageType.patternRegex")


  def main(args: Array[String]): Unit = {
    val one = new IntWritable(1) // Integer datatype of hadoop
    val word = new Text() // Apache text object, store text using standard UTF8 encoding

    val value = "10:44:58.185 [scala-execution-context-global-17] INFO  HelperUtils.Parameters$ - Y*h\\7~`A?aghsCRwJ_\"fx$\n10:44:58.201 [scala-execution-context-global-17] INFO  HelperUtils.Parameters$ - i}Vy9/vLaghsR4yV2?]h\n10:44:58.687 [scala-execution-context-global-17] INFO  HelperUtils.Parameters$ - nwvL^YWh1111t{Q,~b}~\n10:44:58.702 [scala-execution-context-global-17] INFO  HelperUtils.Parameters$ - '/Q?hcN1111|o4=>\">\n10:44:58.717 [scala-execution-context-global-17] INFO  HelperUtils.Parameters$ - :UB!L_~!K<1111G$-vp`?a8H"
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
              println(word)
              println(new IntWritable(str.split(" +").last.length()))

//              context.write(word, new IntWritable(str.split(" +").last.length())) // Key = MESSAGE TYPE, VALUE = Log message's length
            }
            case None => // If log message type pattern is not found - Do nothing
          }


        }
        case None => // If pattern not found - Do nothing
      }


    }
  }
}
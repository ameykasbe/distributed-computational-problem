import java.util.StringTokenizer
import scala.util.matching.Regex
import org.apache.hadoop.io.{IntWritable, Text}
import org.joda.time.LocalTime
import com.typesafe.config.*
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper
import org.joda.time.LocalTime
import com.github.nscala_time.time.Imports.*

import java.util.StringTokenizer
import com.typesafe.config.*
import org.apache.hadoop.fs.Path

import java.lang.Iterable
import scala.collection.JavaConverters._

object Practice2 {

  val config = ConfigFactory.load()
  val initialTime = config.getString("DistributionMessageType.initialTime")
  val finalTime = config.getString("DistributionMessageType.finalTime")
  val timeRegex = config.getString("DistributionMessageType.timeRegex")
  val messageTypeRegex = config.getString("DistributionMessageType.messageTypeRegex")
  val patternRegex = config.getString("DistributionMessageType.patternRegex")


  def main(args: Array[String]): Unit = {
//    val values: Iterable[IntWritable] = List(new IntWritable(1),new IntWritable(2),new IntWritable(3)).toIterable
//    println(values.asScala.max)
//    new IntWritable(1),new IntWritable(2),new IntWritable(3)

//    val list = new java.util.ArrayList[String]()
//
//    // Adding Strings to the list
//    list.add("geeks")
//    list.add("for")
//    list.add("geeks")
//
//    // Converting list to an Iterable
//    val iterab= list.toIterable
//
//    // Displays output
//    println(iterab)

    val set = Set(new IntWritable(1),new IntWritable(2),new IntWritable(3))
    val values = set.toIterable
    println(values.getClass.toString())
  }
}
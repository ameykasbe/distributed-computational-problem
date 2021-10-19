package mappers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper
import java.util.StringTokenizer

class DistributionMapper extends Mapper[Object, Text, Text, IntWritable] {

  val one = new IntWritable(1) // Integer datatype of hadoop
  val word = new Text() // Store text using standard UTF8 encoding

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
    val pattern = "(INFO|DEBUG|WARN|ERROR)".r // scala.util.matching.Regex instance with pattern as string mentioned
    val matches = pattern.findAllIn(value.toString).mkString(" ") // First, converts the value from Apache Text data type to String datatype. Returns an iterator with all the elements delimited by space.
    val itr = new StringTokenizer(matches) // Tokenizes using default delimiter set, which is " \t\n\r\f". Elements are in String
    val one = new IntWritable(1) // Integer datatype of hadoop
    val word = new Text() // Apache text object, store text using standard UTF8 encoding

    while (itr.hasMoreTokens()) {
      word.set(itr.nextToken()) // Converting String to Apache Text data type
      context.write(word, one)
    }
  }
}

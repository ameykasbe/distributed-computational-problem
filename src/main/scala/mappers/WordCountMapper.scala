package mappers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

import java.util.StringTokenizer

class WordCountMapper extends Mapper[Object, Text, Text, IntWritable] {

  val one = new IntWritable(1)
  val word = new Text()

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
    val itr = new StringTokenizer(value.toString)
    while (itr.hasMoreTokens()) {
      word.set(itr.nextToken())
      context.write(word, one)
    }
  }
}

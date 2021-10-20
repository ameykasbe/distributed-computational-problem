package reducers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer
import java.lang.Iterable
import scala.collection.JavaConverters._

class MaxCharacterReducer extends Reducer[Text,IntWritable,Text,IntWritable] {
  override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
//    var maximum = values.asScala.max
//    context.write(key, maximum)
//    var maximum = values.asScala.toList.max
//    context.write(key, new IntWritable(maximum.get()))
//    reduceLeft(_ max _)
//    var sum = values.asScala.reduceLeft(_ max _)
//    context.write(key, new IntWritable(sum))
//    val values_list = values.asScala.toList
//    val max = 0
//    val values_list_int = values_list.map(x => {
//      val a = x.get()
//      if (a > max){
//        max = a
//      }
//    }
//    )
//
//    context.write(key, new IntWritable(maximum))

    var maximum = values.asScala.foldLeft(0)(_ max _.get)
    context.write(key, new IntWritable(maximum)
  }
}
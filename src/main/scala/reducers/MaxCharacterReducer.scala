package reducers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer
import java.lang.Iterable
import scala.collection.JavaConverters._
import org.slf4j.{LoggerFactory, Logger}

/**
 * A reducer class for the MapReduce Job 4: Number of characters in each log message for each log message type that contain the highest number of characters in the detected instances of the designated regex pattern.
 */
class MaxCharacterReducer extends Reducer[Text,IntWritable,Text,IntWritable] {
  // Create logger
  val logger: Logger = LoggerFactory.getLogger(getClass)

  /**
   * A reducer method for the MapReduce Job 4. Takes input of messageType: Text as key and Iterable of IntWritable(n), where n is the length of string, one element of iterable per each instance of the particular messageType received from different mappers. Returns a key value pair with messageType: Text as key and maximum of all the elements of Iterable as value.
   */

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

    // Calculate maximum of all the elements of the iterable.
    val maximum = values.asScala.foldLeft(0)(_ max _.get)

    // Write output in context variable
    context.write(key, new IntWritable(maximum))  // Key = MESSAGE TYPE, VALUE = maximum of all the elements of iterable
    logger.info(s"Reducer Output: ${key},${maximum}")
  }
}
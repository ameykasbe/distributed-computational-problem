package reducers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer
import java.lang.Iterable
import scala.collection.JavaConverters._
import org.slf4j.{LoggerFactory, Logger}

/**
 * A reducer class for the MapReduce Job 1: Distribution of message types in logs with messages of a particular pattern across predefined time interval.
 */

class DistributionReducer extends Reducer[Text,IntWritable,Text,IntWritable] {
  // Create logger
  val logger: Logger = LoggerFactory.getLogger(getClass)

  /**
   * A reducer method for the MapReduce Job 1. Takes input of messageType: Text as key and Iterable of IntWritable(n) e.g. 1 as value, one element of iterable per each instance of the particular messageType received from different mappers. Returns a key value pair with messageType: Text as key and sum of all the elements of Iterable as value.
   */

  override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
    // Calculate sum of all the elements of the iterable.
    var sum = values.asScala.foldLeft(0)(_ + _.get)
    // Write in context variable
    context.write(key, new IntWritable(sum)) // Key = MESSAGE TYPE, VALUE = sum of all the elements of iterable
    logger.info(s"Reducer Output: ${key},${sum}")
  }

}
import java.lang.Iterable
import java.util.StringTokenizer

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.collection.JavaConverters._
import com.typesafe.config._
import mappers._
import reducers._
import org.slf4j.{LoggerFactory, Logger}

object Execution{


  def main(args: Array[String]): Unit = {
    // Create logger
    val logger: Logger = LoggerFactory.getLogger(getClass)

    // Create a configuration object for MapReduce job
    val configuration = new Configuration

    // Configure output format as a comma separated format
    configuration.set("mapred.textoutputformat.separator", ",")

    // Load custom configuration
    val config = ConfigFactory.load()

    logger.info("Starting execution of computational problem in a MapReduce distributed system.")

    // Based on argument passed by command, execute the particular job
    if (args(0) == "1") {
      logger.info("Starting execution of Job 1: Distribution of message types in logs with messages of a particular pattern across predefined time interval.")

      // Creating a MapReduce job
      val mapReduceJob = Job.getInstance(configuration,"Distribution of message types in logs with messages of a particular pattern across predefined time interval")
      mapReduceJob.setJarByClass(this.getClass)

      // Mapper config
      mapReduceJob.setMapperClass(classOf[DistributionMapper])

      // Reducer config
      mapReduceJob.setCombinerClass(classOf[DistributionReducer])
      mapReduceJob.setReducerClass(classOf[DistributionReducer])

      // Output config
      mapReduceJob.setOutputKeyClass(classOf[Text])
      mapReduceJob.setOutputKeyClass(classOf[Text]);
      mapReduceJob.setOutputValueClass(classOf[IntWritable]);

      // Input-Output Path config
      FileInputFormat.addInputPath(mapReduceJob, new Path(args(1)))
      val outputPath = args(2) + config.getString("OutputLocation.DistributionMessageType")
      FileOutputFormat.setOutputPath(mapReduceJob, new Path(outputPath))

      // Exit system if job completed
      System.exit(if(mapReduceJob.waitForCompletion(true))  0 else 1)
    }

    if (args(0) == "3") {
      logger.info("Starting execution of Job 3: Distribution of message types in logs with messages of a particular pattern.")

      // Creating a MapReduce job
      val mapReduceJob = Job.getInstance(configuration,"Distribution of message types in logs with messages of a particular pattern")
      mapReduceJob.setJarByClass(this.getClass)

      // Mapper config
      mapReduceJob.setMapperClass(classOf[DistributionMapperNoTime])

      // Reducer config
      mapReduceJob.setCombinerClass(classOf[DistributionReducerNoTime])
      mapReduceJob.setReducerClass(classOf[DistributionReducerNoTime])

      // Output config
      mapReduceJob.setOutputKeyClass(classOf[Text])
      mapReduceJob.setOutputKeyClass(classOf[Text]);
      mapReduceJob.setOutputValueClass(classOf[IntWritable]);

      // Input-Output Path config
      FileInputFormat.addInputPath(mapReduceJob, new Path(args(1)))
      val outputPath = args(2) + config.getString("OutputLocation.DistributionMessageTypeNoTime")
      FileOutputFormat.setOutputPath(mapReduceJob, new Path(outputPath))

      // Exit system if job completed
      System.exit(if(mapReduceJob.waitForCompletion(true))  0 else 1)
    }

    if (args(0) == "4") {
      logger.info("Starting execution of Job 4: Number of characters in each log message for each log message type that contain the highest number of characters in the detected instances of the designated regex pattern.")

      // Creating a MapReduce job
      val mapReduceJob = Job.getInstance(configuration,"Number of characters in each log message for each log message type that contain the highest number of characters in the detected instances of the designated regex pattern")
      mapReduceJob.setJarByClass(this.getClass)

      // Mapper config
      mapReduceJob.setMapperClass(classOf[MaxCharacterMapper])

      // Reducer config
      mapReduceJob.setCombinerClass(classOf[MaxCharacterReducer])
      mapReduceJob.setReducerClass(classOf[MaxCharacterReducer])

      // Output config
      mapReduceJob.setOutputKeyClass(classOf[Text])
      mapReduceJob.setOutputKeyClass(classOf[Text]);
      mapReduceJob.setOutputValueClass(classOf[IntWritable]);

      // Input-Output Path config
      FileInputFormat.addInputPath(mapReduceJob, new Path(args(1)))
      val outputPath = args(2) + config.getString("OutputLocation.MaxCharacter")
      FileOutputFormat.setOutputPath(mapReduceJob, new Path(outputPath))

      // Exit system if job completed
      System.exit(if(mapReduceJob.waitForCompletion(true))  0 else 1)
    }
  }
}

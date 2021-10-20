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

object Execution{


  def main(args: Array[String]): Unit = {
    val configuration = new Configuration
    configuration.set("mapred.textoutputformat.separator", ",")
    val config = ConfigFactory.load()

    if (args(0) == "1") {
      val distributionJob = Job.getInstance(configuration,"Distribution message types limited by time duration")
      distributionJob.setJarByClass(this.getClass)
      // Mapper config
      distributionJob.setMapperClass(classOf[DistributionMapper])
      distributionJob.setCombinerClass(classOf[DistributionReducer])
      // Reducer config
      distributionJob.setReducerClass(classOf[DistributionReducer])
      distributionJob.setOutputKeyClass(classOf[Text])
      distributionJob.setOutputKeyClass(classOf[Text]);
      distributionJob.setOutputValueClass(classOf[IntWritable]);
      FileInputFormat.addInputPath(distributionJob, new Path(args(1)))
      val outputPath = config.getString("OutputLocation.MainLocation") + config.getString("OutputLocation.DistributionMessageType")
      FileOutputFormat.setOutputPath(distributionJob, new Path(outputPath))
      println("Starting MapReduce job...")
      System.exit(if(distributionJob.waitForCompletion(true))  0 else 1)
    }

    if (args(0) == "3") {
      val distributionJob = Job.getInstance(configuration,"Distribution message types NOT limited by time duration")
      distributionJob.setJarByClass(this.getClass)
      // Mapper config
      distributionJob.setMapperClass(classOf[DistributionMapperNoTime])
      distributionJob.setCombinerClass(classOf[DistributionReducerNoTime])
      // Reducer config
      distributionJob.setReducerClass(classOf[DistributionReducerNoTime])
      distributionJob.setOutputKeyClass(classOf[Text])
      distributionJob.setOutputKeyClass(classOf[Text]);
      distributionJob.setOutputValueClass(classOf[IntWritable]);
      FileInputFormat.addInputPath(distributionJob, new Path(args(1)))
      val outputPath = config.getString("OutputLocation.MainLocation") + config.getString("OutputLocation.DistributionMessageTypeNoTime")
      FileOutputFormat.setOutputPath(distributionJob, new Path(outputPath))
      println("Starting MapReduce job...")
      System.exit(if(distributionJob.waitForCompletion(true))  0 else 1)
    }



  }
}

import java.util.StringTokenizer
import scala.util.matching.Regex
import org.apache.hadoop.io.{IntWritable, Text}
import com.github.nscala_time.time.Imports.*
import org.joda.time.LocalTime
import com.typesafe.config._


object Practice {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val initialTime = config.getString("DistributionMessageType.initialTime")
    val finalTime = config.getString("DistributionMessageType.finalTime")
    val timeRegex = config.getString("DistributionMessageType.timeRegex")
    val messageTypeRegex = config.getString("DistributionMessageType.messageTypeRegex")
    val patternRegex = config.getString("DistributionMessageType.patternRegex")
  }
}


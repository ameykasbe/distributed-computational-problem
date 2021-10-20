package utils
import com.typesafe.config.ConfigFactory
import org.joda.time.LocalTime
import java.util.StringTokenizer
import utils.ParsingUtil

class ParsingUtil (regex: String, string: String){

  def findPattern(): Option[String]={
    val pattern = regex.r // Create scala.util.matching.Regex instance
    pattern.findFirstIn(string) // Finds first occurence of pattern and returns Option[String]
  }

}

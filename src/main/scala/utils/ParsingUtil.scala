package utils
import com.typesafe.config.ConfigFactory
import org.joda.time.LocalTime
import java.util.StringTokenizer
import utils.ParsingUtil


/**
 * A utility class to find a pattern in a string.
 */
class ParsingUtil (regex: String, string: String){

  /**
   * Returns Option[String] instance with the first occurance of pattern in the String.
   */
  def findPattern(): Option[String]={
    // Create scala.util.matching.Regex instance
    val pattern = regex.r

    // Finds first occurence of pattern and return Option[String]
    pattern.findFirstIn(string)
  }

}

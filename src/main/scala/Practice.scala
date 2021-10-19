import java.util.StringTokenizer
import scala.util.matching.Regex
import org.apache.hadoop.io.{IntWritable, Text}

object Practice {
  def main(args: Array[String]): Unit ={
    // REGEX
    // val pattern = "INFO".r // scala.util.matching.Regex instance with pattern as string mentioned
    // val str = "09:58:55.569 [main] INFO  GenerateLogData$ - Log data generator started...\n09:58:55.881 [scala-execution-context-global-17] INFO  GenerateLogData$ - NL8Q%rvl,RBHq@|XR2U&k>\"SXwcyB#iv\n09:58:55.928 [scala-execution-context-global-17] WARN  Generation.Parameters$ - =5$YcP!s@h\n09:59:30.849 [scala-execution-context-global-17] INFO  Generation.Parameters$ - V<Z~#Ws\"WNJ:[d?+dRpaIFp23\"1_oKn;Qd,>\n09:59:30.867 [scala-execution-context-global-17] INFO  Generation.Parameters$ - 3FNgL<)k7+c+8yQ\"3m*e#!)HK[['z+-an/Uw?J'|[<w&kbtM\n09:59:30.876 [scala-execution-context-global-17] ERROR  Generation.Parameters$ - +5l}CAK:}q])\n09:59:30.891 [scala-execution-context-global-17] INFO  Generation.Parameters$ - Mv8)!{uuaD3%<m.VO/[pfHLS&eIBmKx~(6"
    // val obj = pattern.findAllIn(str) // Returns an iterator with all the elements
    // println(obj.mkString(",")) // Returns string of the iterator, where elements are separated by argument (,)
    // println(obj.count(_ => true)) // Counts objects

    // MAPPER
//    val one = new IntWritable(1) // Integer datatype of hadoop
//    val word = new Text() // Apache text object, store text using standard UTF8 encoding
//    val value = "INFO ERROR WARN"
//    val itr = new StringTokenizer(value) // Tokenizes using default delimiter set, which is " \t\n\r\f". Elements are in String
//
//    while (itr.hasMoreTokens()) {
//      word.set(itr.nextToken()) // Converting String to Apache Text data type
//      //      context.write(word, one)
//    }

    val pattern = "(INFO|DEBUG|WARN|ERROR)".r // scala.util.matching.Regex instance with pattern as string mentioned
    val value = "09:58:55.569 [main] INFO  GenerateLogData$ - Log data generator started...\n09:58:55.881 [scala-execution-context-global-17] INFO  GenerateLogData$ - NL8Q%rvl,RBHq@|XR2U&k>\"SXwcyB#iv\n09:58:55.928 [scala-execution-context-global-17] WARN  Generation.Parameters$ - =5$YcP!s@h\n09:59:30.849 [scala-execution-context-global-17] INFO  Generation.Parameters$ - V<Z~#Ws\"WNJ:[d?+dRpaIFp23\"1_oKn;Qd,>\n09:59:30.867 [scala-execution-context-global-17] INFO  Generation.Parameters$ - 3FNgL<)k7+c+8yQ\"3m*e#!)HK[['z+-an/Uw?J'|[<w&kbtM\n09:59:30.876 [scala-execution-context-global-17] ERROR  Generation.Parameters$ - +5l}CAK:}q])\n09:59:30.891 [scala-execution-context-global-17] INFO  Generation.Parameters$ - Mv8)!{uuaD3%<m.VO/[pfHLS&eIBmKx~(6"
    val matches = pattern.findAllIn(value.toString).mkString(" ") // First, converts the value from Apache Text data type to String datatype. Returns an iterator with all the elements delimited by space.
    val itr = new StringTokenizer(matches) // Tokenizes using default delimiter set, which is " \t\n\r\f". Elements are in String

    val one = new IntWritable(1) // Integer datatype of hadoop
    val word = new Text() // Apache text object, store text using standard UTF8 encoding

    while (itr.hasMoreTokens()) {
      word.set(itr.nextToken()) // Converting String to Apache Text data type
      println(word)
//      context.write(word, one)
    }

  }
}

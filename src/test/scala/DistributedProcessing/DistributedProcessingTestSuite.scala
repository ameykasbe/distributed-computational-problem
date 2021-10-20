package DistributedProcessing
import org.scalatest.funspec.AnyFunSpec
import com.typesafe.config.ConfigFactory
import org.joda.time.LocalTime
import utils.ParsingUtil

import java.util.StringTokenizer

class DistributedProcessingTestSuite extends AnyFunSpec {
  val config = ConfigFactory.load()

  describe("Config file") {
    it("should be present") {
      assert(!config.isEmpty)
    }
  }



  describe("Config parameters"){
    it("should have correct regex for parsing time") {
      assert(config.getString("DistributionMessageType.timeRegex") == "\\d{2}:\\d{2}:\\d{2}.\\d{3}")
    }
    it("should have correct regex for parsing message type") {
      assert(config.getString("DistributionMessageType.messageTypeRegex") == "(INFO|DEBUG|WARN|ERROR)")
    }
    it("should have correct regex for parsing pattern") {
      assert(config.getString("DistributionMessageType.patternRegex") == "([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}")
    }
  }




  val testLogPattern = "19:54:46.593 [scala-execution-context-global-13] INFO  HelperUtils.Parameters$ - 8G;,3m_T`G#H]&Yh:Ei1%fp''5`af0ce3H6sA5hM8qR7hae3bf1H8jV8j%<Bqz8fMm#{JWqMdoc_2N/|wf8]"

  val testLogNoPattern = "19:54:46.561 [scala-execution-context-global-13] WARN  HelperUtils.Parameters$ - k1H+R#/%d0>Q>N:6\\enT]U"




  val patternRegex = config.getString("DistributionMessageType.patternRegex")
  val parsingUtil1 = new ParsingUtil(patternRegex, testLogPattern)
  val customPattern1 = parsingUtil1.findPattern()

  describe("Parse pattern from message") {
    it("should find pattern in message") {
      customPattern1 match{
        case Some(sp) => {
          assert(sp == "af0ce3H6sA5hM8qR7hae3bf1H8jV8j")
        }
        case None => assert(false)
      }
    }

  }




  val parsingUtil2 = new ParsingUtil(patternRegex, testLogNoPattern)
  val customPattern2 = parsingUtil2.findPattern()
  describe("Parse pattern from message with no match") {
    it("should not find pattern in message") {
      customPattern2 match{
        case Some(spn) => assert(false)
        case None => assert(true)
      }
    }
  }





  val messageTypeRegex = config.getString("DistributionMessageType.messageTypeRegex")
  val parsingUtil3 = new ParsingUtil(messageTypeRegex, testLogPattern)
  val matchMessageType = parsingUtil3.findPattern()

  describe("Parse message type log") {
    it("should find the correct message type") {
      matchMessageType match {
        case Some(sm) => {
          assert(sm == "INFO")
        }
        case None => assert(false)
      }
    }
  }


}

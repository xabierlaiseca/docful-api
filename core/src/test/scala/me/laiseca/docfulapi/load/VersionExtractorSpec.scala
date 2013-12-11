package me.laiseca.docfulapi.load

import org.scalatest.FlatSpec
import me.laiseca.docfulapi.model.Version

class VersionExtractorSpec extends FlatSpec {
  
  "given a version map with entries for all the values" should "create a complete Version instance" in {
    val versionMap = Map[String, String]("since" -> "0.0.1",
        "deprecated" -> "0.0.2", "until" -> "0.0.3")
    val testObj = new Object with VersionExtractor
        
    assertResult(new Version(Option.apply("0.0.1"), 
        Option.apply("0.0.2"), Option.apply("0.0.3"))){
      testObj.extractVersion(versionMap)
    }
  }
  
  "given a version map with entries for none of the values" should "create an empty Version instance" in {
    val versionMap = Map[String, String]()
    val testObj = new Object with VersionExtractor
        
    assertResult(new Version(Option.empty, Option.empty, Option.empty)){
      testObj.extractVersion(versionMap)
    }
  }
  
  "given an empty option map with entries for none of the values" should "create an empty Version instance" in {
    val versionMap = Option.empty[Map[String, String]]
    val testObj = new Object with VersionExtractor
        
    assertResult(new Version(Option.empty, Option.empty, Option.empty)){
      testObj.extractVersion(versionMap)
    }
  }
}
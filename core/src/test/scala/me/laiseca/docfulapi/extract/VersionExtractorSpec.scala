package me.laiseca.docfulapi.extract

import org.scalatest.FlatSpec
import me.laiseca.docfulapi.model.Version

class VersionExtractorSpec extends FlatSpec {
  private val firstVersion = "0.0.1"
  private val lastVersion = "0.0.5"
  
  "given a version map with entries for all the values" should "create a complete Version instance" in {
    val versionMap = Map[String, String]("since" -> "0.0.2",
        "deprecated" -> "0.0.3", "until" -> "0.0.4")
    val testObj = new VersionExtractorObject(firstVersion, lastVersion)
        
    assertResult(new Version("0.0.2", Option("0.0.3"), "0.0.4")) {
      testObj.extractVersion(versionMap)
    }
  }
  
  "given a version map with entries for none of the values" should "create a Version instance with required fields" in {
    val versionMap = Map[String, String]()
    val testObj = new VersionExtractorObject(firstVersion, lastVersion)
        
    assertResult(new Version(firstVersion, Option.empty, lastVersion)) {
      testObj.extractVersion(versionMap)
    }
  }
  
  "given an empty option map with entries for none of the values" should "create a Version instance with required fields" in {
    val versionMap = Option.empty[Map[String, String]]
    val testObj = new VersionExtractorObject(firstVersion, lastVersion)
        
    assertResult(new Version(firstVersion, Option.empty, lastVersion)) {
      testObj.extractVersion(versionMap)
    }
  }
}

case class VersionExtractorObject(protected val firstVersion:String, protected val lastVersion:String) extends VersionExtractor
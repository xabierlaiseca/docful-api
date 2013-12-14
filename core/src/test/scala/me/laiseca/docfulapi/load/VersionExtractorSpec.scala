package me.laiseca.docfulapi.load

import org.scalatest.FlatSpec
import me.laiseca.docfulapi.model.Version
import me.laiseca.docfulapi.model.Versionable

class VersionExtractorSpec extends FlatSpec {
  private val firstVersion = "0.0.1"
  private val lastVersion = "0.0.5"
  
  "given a version map with entries for all the values" should "create a complete Version instance" in {
    val versionMap = Map[String, String]("since" -> "0.0.1",
        "deprecated" -> "0.0.2", "until" -> "0.0.3")
    val testObj = new VersionExtractorObject(firstVersion, lastVersion)
        
    assertResult(new Version("0.0.1", Option.apply("0.0.2"), "0.0.3")) {
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
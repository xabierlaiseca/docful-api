package me.laiseca.docfulapi.extract

import org.scalatest.FlatSpec

class VersionsExtractorSpec extends FlatSpec {
  "given a map without versions" should "return an empty list" in {
    val versions = Map[String,Any]()
    
    val extractor = new VersionsExtractor
    assertResult(List()) {
      extractor.extract(versions)
    }
  }
  
  "given a map with versions" should "return the expected version list" in {
    val versions = Map("versions" -> List("0.0.1", "0.0.2"))
    
    val extractor = new VersionsExtractor
    assertResult(List("0.0.1", "0.0.2")) {
      extractor.extract(versions)
    }
  }
}
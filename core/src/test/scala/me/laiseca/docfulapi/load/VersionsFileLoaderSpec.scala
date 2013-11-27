package me.laiseca.docfulapi.load

import org.scalatest.FlatSpec
import org.yaml.snakeyaml.Yaml
import java.io.File

class VersionsLoaderSpec extends FlatSpec {
  private val yaml = new Yaml()
  
  "given a file path to a file with versions defined" should "load multiple versions" in {
    val loader = new VersionsFileLoader(yaml)
    
    assertResult(List("0.0.1", "0.0.2", "0.1.0", "0.2.0", "0.3.0")) {
      loader.load("/versions/versions-multiple.yaml")
    }
  }
  
  "given a file path to a file with no versions" should "load zero versions" in {
    val loader = new VersionsFileLoader(yaml)
    
    assertResult(List()) {
      loader.load("/versions/versions-none.yaml")
    }
  }
  
  "given a file path to an empty file" should "load zero versions" in {
    val loader = new VersionsFileLoader(yaml)
    
    assertResult(List()) {
      loader.load("/versions/versions-empty.yaml")
    }
  }
  
  "given a file path to a not existing file" should "load zero versions" in {
    val loader = new VersionsFileLoader(yaml)
    
    assertResult(List()) {
      loader.load("/versions/versions-netexisting.yaml")
    }
  }
}
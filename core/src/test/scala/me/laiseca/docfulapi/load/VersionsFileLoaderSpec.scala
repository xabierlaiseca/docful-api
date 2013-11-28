package me.laiseca.docfulapi.load

import org.mockito.Mockito.when

import org.scalatest.FlatSpec
import org.yaml.snakeyaml.Yaml
import java.io.File
import org.scalatest.mock.MockitoSugar

class VersionsLoaderSpec extends FlatSpec with MockitoSugar {
  
  "given a file path to a file with versions defined" should "load multiple versions" in {
    val filepath = "/versions/versions-multiple.yaml"
    val yamlLoader = mock[YamlLoader]
    val versions = List("0.0.1", "0.0.2", "0.1.0", "0.2.0", "0.3.0")
    when(yamlLoader.loadAsMap[String, List[String]](filepath))
    	.thenReturn(Option.apply(Map("versions" -> versions)))
    
    val loader = new VersionsFileLoader(yamlLoader)
    
    assertResult(versions) {
      loader.load(filepath)
    }
  }
  
  "given a file path to a file with versions not defined" should "load zero versions" in {
    val filepath = "/versions/versions-none.yaml"
    val yamlLoader = mock[YamlLoader]
    when(yamlLoader.loadAsMap[String, List[String]](filepath))
    	.thenReturn(Option.apply(Map[String,List[String]]()))
    
    val loader = new VersionsFileLoader(yamlLoader)
    
    assertResult(List()) {
      loader.load(filepath)
    }
  }
    
  "given a file path to a not existing file" should "load zero versions" in {
    val filepath = "/versions/versions-notexisting.yaml"
    val yamlLoader = mock[YamlLoader]
    when(yamlLoader.loadAsMap[String, List[String]](filepath))
    	.thenReturn(Option.empty)
    
    val loader = new VersionsFileLoader(yamlLoader)
    
    assertResult(List()) {
      loader.load(filepath)
    }
  }
}
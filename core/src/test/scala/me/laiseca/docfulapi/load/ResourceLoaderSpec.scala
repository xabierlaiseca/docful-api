package me.laiseca.docfulapi.load

import org.mockito.Matchers.any
import org.mockito.Matchers.anyString
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar
import me.laiseca.docfulapi.extract.ResourceExtractor
import me.laiseca.docfulapi.model.Resource

class ResourceLoaderSpec extends FlatSpec with MockitoSugar {
  val firstVersion = "0.0.1"
  val lastVersion = "1.0.0"
  val file1 = "file1"
  val file2 = "file2"
  
  "given an empty list of resource files" should "return an empty list of resources" in {
    val yamlLoader = mock[YamlLoader]
    val extractor = mock[ResourceExtractor]
    
    val loader = new ResourceLoader(firstVersion, lastVersion, extractor, yamlLoader)
    
    assertResult(List()) {
      loader.load(List())
    }
    
    verify(yamlLoader, never).loadAsMap(anyString)
    verify(extractor, never).extract(any(classOf[Map[String,_]]))
  } 
  
  "given a list of resource files" should "return a list with the expected resources" in {
    val yamlLoader = mock[YamlLoader]
    val extractor = mock[ResourceExtractor]
    val resource1 = mock[Resource]; val resource2 = mock[Resource]
    val map1 = Map[String,Any]("map1" -> ""); val map2 = Map[String,Any]("map2" -> "")
    
    when(yamlLoader.loadAsMap[String,Any](file1)).thenReturn(Option(map1))
    when(yamlLoader.loadAsMap[String,Any](file2)).thenReturn(Option(map2))
    when(extractor.extract(map1)).thenReturn(resource1)
    when(extractor.extract(map2)).thenReturn(resource2)
    
    val loader = new ResourceLoader(firstVersion, lastVersion, extractor, yamlLoader)
    
    assertResult(List(resource1, resource2)) {
      loader.load(List(file1, file2))
    }
  } 
}
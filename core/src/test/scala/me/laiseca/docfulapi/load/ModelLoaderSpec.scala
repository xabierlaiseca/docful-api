package me.laiseca.docfulapi.load

import org.mockito.Matchers.anyString
import org.mockito.Matchers.any
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import me.laiseca.docfulapi.model.Model
import org.scalatest.mock.MockitoSugar
import me.laiseca.docfulapi.extract.ModelExtractor
import me.laiseca.docfulapi.model.Type
import me.laiseca.docfulapi.model.Model

class ModelLoaderSpec extends FlatSpec with MockitoSugar {
  val firstVersion = "0.0.1"
  val lastVersion = "1.0.0"
  val file1 = "file1"
  val file2 = "file2"
  
  "given an empty list of model files" should "return a model without types" in {
    val yamlLoader = mock[YamlLoader]
    val extractor = mock[ModelExtractor]
    
    val loader = new ModelLoader(firstVersion, lastVersion, extractor, yamlLoader)
    
    assertResult(Model(List())) {
      loader.load(List())
    }
    
    verify(yamlLoader, never).loadAsMap(anyString)
    verify(extractor, never).extract(any(classOf[Map[String,_]]))
  }
  
  "given a list of model files" should "return a model with expected types" in {
    val yamlLoader = mock[YamlLoader]
    val extractor = mock[ModelExtractor]
    val type11 = mock[Type]; val type21 = mock[Type]; val type22 = mock[Type]
    val map1 = Map[String,Any]("map1" -> ""); val map2 = Map[String,Any]("map2" -> "")
    
    when(yamlLoader.loadAsMap[String,Any](file1)).thenReturn(Option(map1))
    when(yamlLoader.loadAsMap[String,Any](file2)).thenReturn(Option(map2))
    when(extractor.extract(map1)).thenReturn(new Model(List(type11)))
    when(extractor.extract(map2)).thenReturn(new Model(List(type21, type22)))
    
    val loader = new ModelLoader(firstVersion, lastVersion, extractor, yamlLoader)
    
    assertResult(Model(List(type21, type22, type11))) {
      loader.load(List(file1, file2))
    }
  }
}
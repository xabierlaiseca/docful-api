package me.laiseca.docfulapi.load

import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar
import me.laiseca.docfulapi.extract.VersionsExtractor
import me.laiseca.docfulapi.model.Versions

class VersionsLoaderSpec extends FlatSpec with MockitoSugar {
  val firstVersion = "0.0.1"
  val secondVersion = "0.1.0"
  val lastVersion = "0.2.0"
  val filepath = "file1"
  
  "given a versions file" should "return the sorted list of versions" in {
    val yamlLoader = mock[YamlLoader]
    val extractor = mock[VersionsExtractor]
    val map = Map("map" -> "")
    
    when(yamlLoader.loadAsMap[String,Any](filepath)).thenReturn(Option(map))
    when(extractor.extract(map)).thenReturn(List(secondVersion, firstVersion, lastVersion))
    
    val loader = new VersionsLoader(extractor, yamlLoader)
    
    assertResult(Versions(List(firstVersion, secondVersion, lastVersion))) {
      loader.load(filepath)
    }
  }
}
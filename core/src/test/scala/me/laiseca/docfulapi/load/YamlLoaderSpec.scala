package me.laiseca.docfulapi.load

import org.scalatest.FlatSpec

class YamlLoaderSpec extends FlatSpec {
  "given a file with yaml basic content" should "parse it and return a map" in {
    val testObj =  new YamlLoader()
    
    assertResult(Map("firstname" -> "Xabier", "surname" -> "Laiseca", "over18" -> true, "age" -> 30)) {
      testObj.loadAsMap[String,String]("/yaml/yaml-loader-basic.yaml").orNull
    }
  }

  "given a file with yaml complex content" should "parse it and return a map" in {
    val testObj =  new YamlLoader()
    
    assertResult(Map("firstnames" -> List("Xabier", "Iraide"))) {
      testObj.loadAsMap[String,List[String]]("/yaml/yaml-loader-complex.yaml").orNull
    }
  }
}
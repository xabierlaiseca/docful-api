package me.laiseca.docfulapi.extract

import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar
import me.laiseca.docfulapi.model.Model
import me.laiseca.docfulapi.model.Type
import me.laiseca.docfulapi.model.Version
import me.laiseca.docfulapi.model.Property
import me.laiseca.docfulapi.load.YamlLoader

class ModelExtractorSpec extends FlatSpec {
  private val firstVersion = "0.0.1"
  private val lastVersion = "0.1.0"

  "given a model type with no properties" should "extract the fields and create a model object" in {
    val model = 
      Map("model" -> List(
        Map("name" -> "User"))
      )
    
    val extractor = new ModelExtractor(firstVersion, lastVersion)
    assertResult{
      new Model(List(new Type("User", Option.empty, List(), Version.apply(firstVersion, lastVersion))))
    } {
      extractor.extract(model)
    }
  } 
  
  "given a model type with one property and only the required fields" should "extract the fields and create a model object" in {
    val model = 
      Map("model" -> List(
    	Map("name" -> "User", "properties" -> List(
    	  Map("name" -> "prop", "type" -> "string", "optional" -> true)
    	))
      ))
    
    val extractor = new ModelExtractor(firstVersion, lastVersion)
    assertResult{new Model(List(new Type("User", Option.empty, 
        List(new Property("prop", "string", true, Option.empty, Version.apply(firstVersion, lastVersion))),
        Version.apply(firstVersion, lastVersion))))
    } {
      extractor.extract(model)
    }
  } 
  
  "given a model type with one property and all the fields" should "extract the fields and create a model object" in {
    val model = 
      Map("model" -> List(
    	Map("name" -> "User", "desc" -> "description", "properties" -> List(
    	  Map("name" -> "prop", "type" -> "string", "optional" -> true, "desc" -> "desc2")
    	))
      ))
    
    val extractor = new ModelExtractor(firstVersion, lastVersion)
    assertResult{new Model(List(new Type("User", Option.apply("description"), 
        List(new Property("prop", "string", true, Option.apply("desc2"), Version.apply(firstVersion, lastVersion))),
        Version.apply(firstVersion, lastVersion))))
    } {
      extractor.extract(model)
    }
  }
  
  "given a model type with one property, versions and all the fields" should "extract the fields and create a model object" in {
    val model = 
      Map("model" -> List(
    	Map("name" -> "User", "desc" -> "description", "properties" -> 
    	  List(
    	    Map("name" -> "prop", "type" -> "string", "optional" -> true, "desc" -> "desc2",
    	        "version" -> Map("since" -> "0.0.2", "deprecated" -> "0.0.3", "until" -> "0.0.4"))
    	  ),
    	  "version" -> Map("since" -> "0.0.6", "deprecated" -> "0.0.7", "until" -> "0.0.8")
    	)
      ))
    
    val extractor = new ModelExtractor(firstVersion, lastVersion)
    assertResult{new Model(List(new Type("User", Option.apply("description"), 
        List(new Property("prop", "string", true, Option.apply("desc2"),
          new Version("0.0.2", Option.apply("0.0.3"), "0.0.4"))),
        new Version("0.0.6", Option.apply("0.0.7"), "0.0.8"))))
    } {
      extractor.extract(model)
    }
  }
}

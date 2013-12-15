package me.laiseca.docfulapi.extract

import org.scalatest.FlatSpec
import me.laiseca.docfulapi.model.Resource
import me.laiseca.docfulapi.model.Version
import me.laiseca.docfulapi.model.Method
import me.laiseca.docfulapi.model.Response
import me.laiseca.docfulapi.model.Parameter

class ResourceExtractorSpec extends FlatSpec {
  private val firstVersion = "0.0.1"
  private val lastVersion = "0.1.0"
  
  "given a map representing a resource with only required fields" should "create a resource object" in {
    val resource =
      Map("path" -> "/pets",
        "methods" -> List(Map("http_method" -> "GET", "responses" -> List(Map("code" -> 200))))
      )
    
    val extractor = new ResourceExtractor(firstVersion, lastVersion)
    assertResult {
      new Resource("/pets", Option.empty, Version(firstVersion, lastVersion),
        List(new Method("GET", Option.empty, Version(firstVersion, lastVersion), List(), 
          List(new Response(200, Option.empty, Option.empty, Version(firstVersion, lastVersion))))),
        List())
    } {
      extractor.extract(resource)
    }
  }
  
  "given a map representing a resource with only required fields and a parameter" should "create a resource object" in {
    val resource =
      Map("path" -> "/pets",
        "methods" -> List(Map("http_method" -> "GET",
          "parameters" -> List(Map("name" -> "param", "type" -> "string")),
          "responses" -> List(Map("code" -> 200))))
      )
    
    val extractor = new ResourceExtractor(firstVersion, lastVersion)
    assertResult {
      new Resource("/pets", Option.empty, Version(firstVersion, lastVersion),
        List(new Method("GET", Option.empty, Version(firstVersion, lastVersion), 
          List(new Parameter("param", "string", Option.empty, false, Version(firstVersion, lastVersion))), 
          List(new Response(200, Option.empty, Option.empty, Version(firstVersion, lastVersion))))),
        List())
    } {
      extractor.extract(resource)
    }
  }
  
  "given a map representing a resource with only required fields and a subresource" should "create a resource object" in {
    val resource =
      Map("path" -> "/pets",
        "methods" -> List(Map("http_method" -> "GET", "responses" -> List(Map("code" -> 204)))),
        "resources" -> List(Map("path" -> "/:id/toys",
          "methods" -> List(Map("http_method" -> "POST", "responses" -> List(Map("code" -> 201))))))
      )
    
    val extractor = new ResourceExtractor(firstVersion, lastVersion)
    assertResult {
      new Resource("/pets", Option.empty, Version(firstVersion, lastVersion),
        List(new Method("GET", Option.empty, Version(firstVersion, lastVersion), List(), 
          List(new Response(204, Option.empty, Option.empty, Version(firstVersion, lastVersion))))),
        List(new Resource("/:id/toys",Option.empty, Version(firstVersion, lastVersion),
          List(new Method("POST", Option.empty, Version(firstVersion, lastVersion), List(), 
            List(new Response(201, Option.empty, Option.empty, Version(firstVersion, lastVersion))))),
          List())))
    } {
      extractor.extract(resource)
    }
  }
  
  "given a map representing a resource with all fields" should "create a resource object" in {
    val resource =
      Map("path" -> "/pets", "desc" -> "desc1",
    	"version" -> Map("since" -> "0.0.2", "deprecated" -> "0.0.3", "until" -> "0.0.4"),
        "methods" -> List(Map("http_method" -> "GET", "desc" -> "desc2",
          "version" -> Map("since" -> "0.0.5", "deprecated" -> "0.0.6", "until" -> "0.0.7"),
          "parameters" -> List(Map("name" -> "param", "type" -> "string", "desc" -> "desc3",
            "version" -> Map("since" -> "0.0.8", "deprecated" -> "0.0.9", "until" -> "0.0.10"))),
          "responses" -> List(Map("code" -> 200, "desc" -> "desc4", "type" -> "int",
            "version" -> Map("since" -> "0.0.11", "deprecated" -> "0.0.12", "until" -> "0.0.13"))))),
        "resources" -> List(Map("path" -> "/:id/toys", "desc" -> "desc5",
          "methods" -> List(Map("http_method" -> "POST", "desc" -> "desc6",
            "responses" -> List(Map("code" -> 201, "desc" -> "desc7", "type" -> "float"))))))
      )
    
    val extractor = new ResourceExtractor(firstVersion, lastVersion)
    assertResult {
      new Resource("/pets", Option("desc1"), new Version("0.0.2", Option("0.0.3"), "0.0.4"),
        List(new Method("GET", Option("desc2"), new Version("0.0.5", Option("0.0.6"), "0.0.7"), 
          List(new Parameter("param", "string", Option("desc3"), false, new Version("0.0.8", Option("0.0.9"), "0.0.10"))), 
          List(new Response(200, Option("desc4"), Option("int"), new Version("0.0.11", Option("0.0.12"), "0.0.13"))))),
        List(new Resource("/:id/toys", Option("desc5"), Version(firstVersion, lastVersion),
          List(new Method("POST", Option("desc6"), Version(firstVersion, lastVersion), List(), 
            List(new Response(201, Option("desc7"), Option("float"), Version(firstVersion, lastVersion))))),
          List())))
    } {
      extractor.extract(resource)
    }
  }
}

//case class Parameter(val name:String, val tpe:String, val desc:Option[String], val optional:Boolean,
//    val version:Version) extends Versionable
//    
//case class Response(val code:Int, val desc:Option[String], val tpe:Option[String], 
//    val version:Version) extends Versionable
package me.laiseca.docfulapi.extract

import me.laiseca.docfulapi.model.Method
import me.laiseca.docfulapi.model.Parameter
import me.laiseca.docfulapi.model.Resource
import me.laiseca.docfulapi.model.Response

class ResourceExtractor(protected val firstVersion:String, protected val lastVersion:String) 
    extends Extractor[Resource] with VersionExtractor {

  def extract(resource:Map[String,_]):Resource = {
    new Resource(
      as("path", resource),
      asOption("desc", resource),
      extractVersion(asOption("version", resource)),
      as[List[Map[String,_]]]("methods", resource).map(method => extractMethod(method)),
      as[List[Map[String,_]]]("resources", List(), resource).map(subresource => extract(subresource))
    )
  }
  
  private def extractMethod(method:Map[String,_]) = {
    new Method(
      as("http_method", method),
      asOption("desc", method),
      extractVersion(asOption("version", method)),
      as[List[Map[String,_]]]("parameters", List(), method).map(parameter => extractParameter(parameter)),
      as[List[Map[String,_]]]("responses", method).map(response => extractResponse(response))
    )
  }
  
  private def extractParameter(parameter:Map[String,_]) = {
    new Parameter(
      as("name", parameter),
      as("type", parameter),
      asOption("desc", parameter),
      as("optional", false, parameter),
      extractVersion(asOption("version", parameter))
    )
  }
  
  private def extractResponse(response:Map[String,_]):Response = {
    new Response(
      as("code", response),
      asOption("desc", response),
      asOption("type", response),
      extractVersion(asOption("version", response))
    )
  }
}

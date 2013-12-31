package me.laiseca.docfulapi.extract

import me.laiseca.docfulapi.model.Versioning

trait VersioningExtractionSupport {
  protected def firstVersion:String
  protected def lastVersion:String
  
  def extractVersion(versionMap: Option[Map[String, String]]):Versioning = {
    extractVersion(versionMap.getOrElse(Map[String, String]()))
  }
  
  def extractVersion(versionMap: Map[String, String]) = {
    new Versioning(versionMap.getOrElse("since", firstVersion), 
        versionMap.get("deprecated"), versionMap.getOrElse("until", lastVersion))
  }
  
}
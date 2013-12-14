package me.laiseca.docfulapi.load

import me.laiseca.docfulapi.model.Version

trait VersionExtractor {
  protected def firstVersion:String
  protected def lastVersion:String
  
  def extractVersion(versionMap: Option[Map[String, String]]):Version = {
    extractVersion(versionMap.getOrElse(Map[String, String]()))
  }
  
  def extractVersion(versionMap: Map[String, String]) = {
    new Version(versionMap.getOrElse("since", firstVersion), 
        versionMap.get("deprecated"), versionMap.getOrElse("until", lastVersion))
  }
  
}
package me.laiseca.docfulapi.load

import me.laiseca.docfulapi.model.Version

trait VersionExtractor {
  def extractVersion(versionMap: Option[Map[String, String]]):Version = {
    extractVersion(versionMap.getOrElse(Map[String, String]()))
  }
  
  def extractVersion(versionMap: Map[String, String]) = {
    new Version(versionMap.get("since"), versionMap.get("deprecated"), versionMap.get("until"))
  }
}
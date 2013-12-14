package me.laiseca.docfulapi.model

case class Version(val since:String, val deprecated:Option[String], val until:String)

object Version {
  def apply(firstVersion:String, lastVersion:String) = {
    new Version(firstVersion, Option.empty, lastVersion)
  }
}

trait Versionable {
  def version(): Version
}
package me.laiseca.docfulapi.model

case class Versioning(val since:String, val deprecated:Option[String], val until:String)

object Versioning {
  def apply(firstVersion:String, lastVersion:String) = {
    new Versioning(firstVersion, Option.empty, lastVersion)
  }
}

trait Versionable {
  def versioning(): Versioning
}
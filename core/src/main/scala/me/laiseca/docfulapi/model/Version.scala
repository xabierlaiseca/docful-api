package me.laiseca.docfulapi.model

case class Version(val since:Option[String], val deprecated:Option[String], val until:Option[String])

trait Versionable {
  def version(): Version
}
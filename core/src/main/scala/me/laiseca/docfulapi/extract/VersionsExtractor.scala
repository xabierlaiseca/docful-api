package me.laiseca.docfulapi.extract

class VersionsExtractor extends Extractor[List[String]] {
  def extract(versions:Map[String,_]):List[String] = {
    versions.getOrElse("versions", List()).asInstanceOf[List[String]]
  }
}
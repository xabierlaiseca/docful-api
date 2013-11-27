package me.laiseca.docfulapi.load

import org.yaml.snakeyaml.Yaml
import java.io.FileInputStream
import scala.collection.JavaConverters._
import java.io.File

class VersionsFileLoader(private val yaml:Yaml) {
  def load(filepath:String):List[String] = {
    val is = getClass().getResourceAsStream(filepath)
    if(is == null) {
      return List()
    }
    
    val versionsFile = yaml.load(is)
      .asInstanceOf[java.util.Map[String, java.util.List[String]]]
    
    if(versionsFile == null) {
      return List()
    }
    
    val versions = versionsFile.get("versions")
    if(versions == null) {
      List()
    } else {
      versions.asScala.toList
    }
  }
}
package me.laiseca.docfulapi.load

import org.yaml.snakeyaml.Yaml
import java.io.FileInputStream
import scala.collection.JavaConverters._
import java.io.File

class VersionsFileLoader(private val loader:YamlLoader = new YamlLoader()) {
  def load(filepath:String):List[String] = {
    val optVersionsFile = loader.loadAsMap[String, List[String]](filepath)
    if(optVersionsFile.isDefined) {
      val versionsFile = optVersionsFile.get
      val versions = versionsFile.get("versions")
      versions.getOrElse(List())
    } else {
      List()
    }
  }
}
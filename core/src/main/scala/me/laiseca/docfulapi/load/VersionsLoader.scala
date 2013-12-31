package me.laiseca.docfulapi.load

import me.laiseca.docfulapi.model.Versions
import me.laiseca.docfulapi.extract.VersionsExtractor
import me.laiseca.docfulapi.model.Versions

class VersionsLoader(private val extractor:VersionsExtractor = new VersionsExtractor,
    private val yamlLoader:YamlLoader = new YamlLoader) {
  
  def load(filepath:String):Versions = {
    val versionsYaml = yamlLoader.loadAsMap[String,Any](filepath).get
    val sortedList = extractor.extract(versionsYaml).sorted
    new Versions(sortedList)
  }
}
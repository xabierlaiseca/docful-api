package me.laiseca.docfulapi.load

import me.laiseca.docfulapi.model.Resource
import me.laiseca.docfulapi.extract.ResourceExtractor

class ResourceLoader(private val fistVersion:String, private val lastVersion:String, 
    private val extractor:ResourceExtractor, private val yaml:YamlLoader = new YamlLoader) {
  def this(firstVersion:String, lastVersion:String) = this(firstVersion, lastVersion,
      new ResourceExtractor(firstVersion, lastVersion))
  
  def load(filepaths:List[String]):List[Resource] = {
    filepaths.map(filepath => yaml.loadAsMap[String,Any](filepath))
    	.map(yamlResource => extractor.extract(yamlResource.get))
  }
}
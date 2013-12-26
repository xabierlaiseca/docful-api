package me.laiseca.docfulapi.load

import me.laiseca.docfulapi.extract.ModelExtractor
import me.laiseca.docfulapi.model.Model
import me.laiseca.docfulapi.model.Type
import me.laiseca.docfulapi.model.Model

class ModelLoader(private val firstVersion:String, private val lastVersion:String, 
    private val extractor:ModelExtractor, private val yamlLoader:YamlLoader = new YamlLoader) {
  
  def this(firstVersion:String, lastVersion:String) = 
    this(firstVersion, lastVersion, new ModelExtractor(firstVersion, lastVersion))
    
  def load(filepaths:List[String]):Model = {
    val models = filepaths.map(filepath => yamlLoader.loadAsMap[String,Any](filepath).get)
      .map(yamlModel => extractor.extract(yamlModel))
    Model(extractTypes(models))
  }

  private def extractTypes(models: List[Model], types: List[Type] = Nil):List[Type] = {
    models match {
      case model::rest => extractTypes(rest, model.types:::types)
      case Nil => types
    }
  }
}
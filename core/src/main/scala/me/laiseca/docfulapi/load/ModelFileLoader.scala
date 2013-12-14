package me.laiseca.docfulapi.load

import me.laiseca.docfulapi.model.Model
import me.laiseca.docfulapi.model.Type
import me.laiseca.docfulapi.model.Property
import me.laiseca.docfulapi.model.Property

class ModelFileLoader(protected val firstVersion:String, protected val lastVersion:String, 
    private val loader:YamlLoader = new YamlLoader()) extends VersionExtractor {
  def load(filepath:String):Model = {
    val yaml = loader.loadAsMap[String, List[_]](filepath)
    if(yaml.isDefined) {
      val model = yaml.get.getOrElse("model", List())
      extractModel(model)
    } else {
      emptyModel
    }
  }
  
  private def extractModel(model:List[_]) = {
    new Model(model.map(elem => extractType(elem.asInstanceOf[Map[String,_]])))
  }
  
  private def extractType(typeMap: Map[String,_]) = {
    val properties = typeMap.getOrElse("properties", List()).asInstanceOf[List[Map[String,_]]]
    new Type(typeMap.get("name").get.asInstanceOf[String],
        typeMap.get("desc").asInstanceOf[Option[String]],
        properties.map(prop => extractProperty(prop)),
        extractVersion(typeMap.get("version").asInstanceOf[Option[Map[String, String]]]))
  }
  
  private def extractProperty(propertyMap: Map[String, _]) = {
    new Property(propertyMap.get("name").get.asInstanceOf[String], propertyMap.get("type").get.asInstanceOf[String],
        propertyMap.getOrElse("optional", false).asInstanceOf[Boolean], propertyMap.get("desc").asInstanceOf[Option[String]],
        extractVersion(propertyMap.get("version").asInstanceOf[Option[Map[String,String]]]))
  }
  
  private def emptyModel() = {
    new Model(List())
  }
}
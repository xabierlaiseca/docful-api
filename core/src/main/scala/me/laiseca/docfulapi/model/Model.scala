package me.laiseca.docfulapi.model

case class Model(val types:List[Type])

case class Type(val name:String, val desc:Option[String], val properties: List[Property], 
    val versioning:Versioning) extends Versionable

case class Property(val name:String, val tpe:String, val optional:Boolean, val desc:Option[String], 
    val versioning:Versioning) extends Versionable

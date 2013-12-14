package me.laiseca.docfulapi.model

case class Resource(val name:String, val desc:Option[String], val version:Version,
    val methods: List[Method], val resources: List[Resource]) extends Versionable

case class Method(val httpMethod: String, val desc:Option[String], val version:Version,
    val parameters: List[Parameter], val responses: List[Response]) extends Versionable
    
case class Parameter(val name:String, val tpe:String, val desc:Option[String], val optional:Boolean,
    val version:Version) extends Versionable
    
case class Response(val code:Int, val desc:Option[String], val tpe:Option[String], 
    val version:Version) extends Versionable

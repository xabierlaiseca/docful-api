package me.laiseca.docfulapi.model

case class Resource(val path:String, val desc:Option[String], val versioning:Versioning,
    val methods: List[Method], val resources: List[Resource]) extends Versionable

case class Method(val httpMethod: String, val desc:Option[String], val versioning:Versioning,
    val parameters: List[Parameter], val responses: List[Response]) extends Versionable
    
case class Parameter(val name:String, val tpe:String, val desc:Option[String], val optional:Boolean,
    val versioning:Versioning) extends Versionable
    
case class Response(val code:Int, val desc:Option[String], val tpe:Option[String], 
    val versioning:Versioning) extends Versionable

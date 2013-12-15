package me.laiseca.docfulapi.extract

trait Extractor[A] {
  def extract(map:Map[String,_]):A
  
  protected def as[T](name:String, map:Map[String,_]):T = {
    map.get(name).get.asInstanceOf[T]
  }
  
  protected def as[T](name:String, default: => T, map:Map[String,_]):T = {
    map.getOrElse(name, default).asInstanceOf[T]
  }
  
  protected def asOption[T](name:String, map:Map[String,_]):Option[T] = {
    map.get(name).asInstanceOf[Option[T]]
  }
}
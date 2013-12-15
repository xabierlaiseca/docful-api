package me.laiseca.docfulapi.extract

import org.scalatest.FlatSpec

class ExtractorSpec extends FlatSpec {
  "given a map with a string value" should "cast it and return it" in {
    val map = Map("key" -> "value", "other1" -> true, "other2" -> 10)
    
    val extractor = new ExtractorObject()
    assertResult("value") {
      extractor.asWrapper[String]("key", map)
    }
  }
  
  "given a map with a string value and a default value" should "cast it and return it" in {
    val map = Map("key" -> "value", "other1" -> true, "other2" -> 10)
    
    val extractor = new ExtractorObject()
    assertResult("value") {
      extractor.asWrapper[String]("key", "", map)
    }
  }
  
  "given a map without string value and a default value" should "return the default value" in {
    val map = Map("other1" -> true, "other2" -> 10)
    
    val extractor = new ExtractorObject()
    assertResult("") {
      extractor.asWrapper[String]("key", "", map)
    }
  }
  
  "given a map with a string value" should "cast it and return it as defined option" in {
    val map = Map("key" -> "value", "other1" -> true, "other2" -> 10)
    
    val extractor = new ExtractorObject()
    assertResult(Option.apply("value")) {
      extractor.asOptionWrapper[String]("key", map)
    }
  }
  
  class ExtractorObject extends Extractor[Any] {
    def extract(map:Map[String,_]) = {
      null
    }
    
    def asWrapper[T](key:String, map:Map[String,_]) = {
      as[T](key, map)
    }
    
    def asWrapper[T](key:String, default: => T, map:Map[String,_]) = {
      as[T](key, default, map)
    }
    
    def asOptionWrapper[T](key:String, map:Map[String,_]) = {
      asOption[T](key, map)
    }
  }
}
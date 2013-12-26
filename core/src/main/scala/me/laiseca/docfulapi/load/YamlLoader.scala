package me.laiseca.docfulapi.load

import org.yaml.snakeyaml.Yaml

import scala.collection.JavaConverters._

class YamlLoader(private val yaml:Yaml = new Yaml()) {
  def loadAsMap[A,B](filepath:String): Option[Map[A,B]] = {
    val is = getClass().getResourceAsStream(filepath)
    if(is == null) {
      Option.empty
    } else {
      Option(toScala(yaml.load(is)).asInstanceOf[Map[A,B]])
    }
  }
  
  private def toScala(obj:Object):Any = {
    obj match {
      case iterable if iterable.isInstanceOf[java.lang.Iterable[_]] => 
        iterable.asInstanceOf[java.lang.Iterable[Object]].asScala.map(subobj => toScala(subobj)).toList
      case map if map.isInstanceOf[java.util.Map[_,_]] =>
        map.asInstanceOf[java.util.Map[Object,Object]].asScala.map(entry => (toScala(entry._1) -> toScala(entry._2))).toMap
      case byte if byte.isInstanceOf[java.lang.Byte] => byte.asInstanceOf[java.lang.Byte].byteValue()
      case short if short.isInstanceOf[java.lang.Short] => short.asInstanceOf[java.lang.Short].shortValue()
      case int if int.isInstanceOf[java.lang.Integer] => int.asInstanceOf[java.lang.Integer].intValue()
      case long if long.isInstanceOf[java.lang.Long] => long.asInstanceOf[java.lang.Long].longValue()
      case float if float.isInstanceOf[java.lang.Float] => float.asInstanceOf[java.lang.Float].floatValue()
      case double if double.isInstanceOf[java.lang.Double] => double.asInstanceOf[java.lang.Double].doubleValue()
      case char if char.isInstanceOf[java.lang.Character] => char.asInstanceOf[java.lang.Character].charValue()
      case boolean if boolean.isInstanceOf[java.lang.Boolean] => boolean.asInstanceOf[java.lang.Boolean].booleanValue()
      case other => other
    }
  }
}
package me.laiseca.docfulapi.model

case class Versions(list:List[String]) {
  def first = list.head
  def last = list.last
}
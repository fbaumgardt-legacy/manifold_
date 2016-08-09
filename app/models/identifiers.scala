/**package models

trait Identifier

trait ImmutableID[K] {
  val address:K
}
trait MutableID[K] {
  var address:K
}
trait ImmutableAddress[V] {
  val record:V
}
trait MutableAddress[V] {
  var record:V
}
trait Typed[T] {
  val typed:T
}
trait Schema[S]

case class PID[A<:String,R<:Record](val address:A, val record:R) extends Identifier with ImmutableID[A] with ImmutableAddress[R]
case class ID[A<:String,R<:Record](val address:A, var record:R) extends Identifier with ImmutableID[A] with MutableAddress[R]
case class Handle[A<:String,R<:Record](var address:A, var record:R) extends Identifier with MutableID[A] with MutableAddress[R]

object JsonFormats {
  import play.api.libs.json.Json
  implicit val pidFormat = Json.format[PID]
  implicit val idFormat = Json.format[ID]
  implicit val handleFormat = Json.format[Handle]
}
  */
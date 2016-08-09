package models

trait Record[I<:Int] { //TODO: id:Identifier
  val id:I
}
  /**
trait ImmutableMetadata[M] {
  val metadata:M
}
trait MutableMetadata[M] {
  var metadata:M
}
trait ImmutableContent[C<:DigitalObject] {
  val content:C
}
trait MutableContent[C<:DigitalObject] {
  var content:C
}
  */
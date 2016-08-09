/**package models

trait DigitalObject
trait MutableObject extends Mutable
trait ImmutableObject extends Immutable

trait Collection extends DigitalObject

trait MutableCollection[T<:Identifier] extends MutableObject with Traversable[T]
trait ImmutableCollection[T<:Identifier] extends ImmutableObject with Traversable[T]
  */
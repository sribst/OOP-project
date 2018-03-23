package common

object ItemType extends Enumeration {
  val BALL = Value
  val POTION = Value
  def apply (v : Value) = v match {
    case BALL => 0
    case POTION => 1
  }
}

case class User (
  val name : String,
  val id : Long,
  var pokemons : Seq[CapturedPokemon]
  //var items : Seq[OwnedItem]
)

case class Pokemon (
  val name : String,
  val id : Long
)

case class CapturedPokemon (
  val base : Pokemon
)

case class Item (
  val name : String,
  val ty   : ItemType.Value,
  val id : Long
)

case class OwnedItem (
  val base : Item,
  val count : Long
)

case class GenPokemon (
  val base : Pokemon,
  val id : Long
)

case class GenItem (
  val base : Item,
  val id : Long
)

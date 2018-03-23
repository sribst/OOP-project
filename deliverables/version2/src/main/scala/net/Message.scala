package net.message

sealed case class Send (a : Any)
sealed case class Request (a : Any)

case object Connect

case class Login (user : String, pwd : String)
case class SignUp (user : String, pwd : String)
case object Logout

case object GetPokemons
case object GetItems
case class MoveTo(x : Double, y : Double)
case object MoveUp
case object MoveDown
case object MoveLeft
case object MoveRight
//case object WhereAmI
case object GetLocation
case class AddCapturedPokemon (id : Long)
case class AddOwnedItem (id : Long, n : Long)

case class Broadcast (m : Any)
case class Positions(list: List[(Double,Double)])
case class Position (x : Double, y : Double)
case object Check
case class Capture (id : Long)
case class DigItem (id : Long)
case object IsAbleToPlay

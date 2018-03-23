package client.io.input

import scala.concurrent.Future

object InputMsg {
  case class Login (u : String, p : String)
  case class SignUp (u : String, p : String)
  case class Location (x : Double, y : Double)
  case object GetPokemons
  case object GetItems
  case object GetLocation
  case object GetHelp
  case object Exit
  case object Check
  case object MoveUp
  case object MoveDown
  case object MoveLeft
  case object MoveRight
  case object Logout
  case object Malformed
  case object Capture
  case object DigItem
}

abstract class InputEngine (val handleInput : Any => Unit) {
  def run : Future[Any]
}

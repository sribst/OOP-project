package client.io.output
import akka.actor.Actor
import scala.concurrent.Future
import common.CapturedPokemon
import common.OwnedItem
import net.message._
object OutputMsg {
  case class InfoText (s : String)
  case class Pokemons (pokemons : Seq[CapturedPokemon])
  case class Items (items : Seq[OwnedItem])
  case class ActualLocation (x : Double, y : Double)
  case object LoginSuccess
  case object LoginFail
  case object SignUpSuccess
  case object SignUpFail
  case object LogoutEvent
  case object ExitEvent
  case object Help
}

class OutputManager extends Actor {
  import OutputMsg._
  import context._

  def display (s : Any) = Future {println (s)}
  def displays (s : Seq[Any]) = Future {
    s foreach (println (_))
  }

  def output : Receive = {
    case InfoText (s) => display (s)
    case LoginSuccess => display ("Login successful !")
    case LoginFail => display ("Login failed.")
    case SignUpSuccess => display ("Sign up successful !")
    case SignUpFail => display ("Sign up failed.")
    case Pokemons (Nil) =>
      display("You don't have any Pokemons.")
    case Pokemons (pokemons) =>
      val msg = Seq("You currently have : ")
      val po = pokemons map (p => " - " + p.base.name)
      displays (msg ++ po)
    case ActualLocation (x,y) =>
      display("You are at : " + x + "," + y)
    case Items (Nil) =>
      display("Your bag is empty.")
    case Items (items) => {
      val msg = Seq("Your bag contains : ")
      val it = items map (i => " - " + i.base.name + " : " + i.count)
      displays (msg ++ it)
    }
    case Help => {
      println("Here is a list of available commands :")
      println("[login <username> <password>]   :: Log in to the game")
      println("[signup <username> <password>]  :: Create an account")
      println("[logout]                        :: Disconnect from your current account")
      println("[pokemons]                      :: Print a list of your Pokemons")
      println("[items]                         :: Print a list of your items")
      println("[up], [down], [left] or [right] :: Move in the named direction")
      println("[whereami]                      :: Get infos on your location")
      println("[check]                         :: Check if a pokemon, or an item, is present")
      println("[capture]                       :: Potentially capture a pokemon")
      println("[dig]                           :: Find an existing item")
      println("[exit]                          :: Exit")
    }
    case LogoutEvent => display ("Logged out")
    case ExitEvent => display ("Exit")
    case MoveUp => {
      println ("Going up!")
    }
    case MoveDown => {
      println ("Going down!")
    }
    case MoveLeft => {
      println ("Going left!")
    }
    case MoveRight => {
      println ("Going right!")
    }


  }

  def receive = output
}

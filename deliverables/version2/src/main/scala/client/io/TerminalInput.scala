package client.io.input.terminal

import scala.util.parsing.combinator._
import scala.concurrent.Future
import client.io.input._

trait InputParser extends RegexParsers {
  import client.io.input.InputMsg._

  val login = """login""".r
  val signup = """signup""".r
  val logout = """logout""".r
  val location = """location""".r
  val pokemons = """pokemons""".r
  val items = """items""".r
  val check = """check""".r
  val up = """up""".r
  val down = """down""".r
  val left = """left""".r
  val right = """right""".r
  val help = """help""".r
  val whereami = """whereami""".r
  val capture = """capture""".r
  val dig = """dig""".r
  val alpha = """[a-zA-Z]+""".r ^^ { s => s }
  val num = """[0-9]+(\.)?[0-9]*""".r ^^ { s => s }
  val exit = """exit""".r

  def action : Parser[Any] = (
      login ~ alpha ~ alpha ^^ { case _ ~ u ~ p => Login (u,p) }
    | signup ~ alpha ~ alpha ^^ { case _ ~ u ~ p => SignUp (u,p) }
    | pokemons ^^ { case _ => GetPokemons }
    | items ^^ { case _ => GetItems }
    | exit ^^ { case _ => Exit }
    | check ^^ { case _ => Check }
    | whereami ^^ { case _ => GetLocation }
    | logout ^^ { case _ => Logout }
    | location ~ num ~ num ^^ { case _ ~ x ~ y => Location(x toDouble,y toDouble)}
    | up ^^ { case _ => MoveUp }
    | down ^^ { case _ => MoveDown }
    | left ^^ { case _ => MoveLeft }
    | right ^^ { case _ => MoveRight }
    | help ^^ { case _ => GetHelp }
    | capture ^^ { case _ => Capture }
    | dig ^^ { case _ => DigItem }
  )
}

class TerminalInputEngine (handler : Any => Unit)
extends InputEngine(handler) with InputParser {
  import scala.io._
  import scala.concurrent.ExecutionContext.Implicits.global

  println("Welcome in Pokemon Go !")
  println("Please type [login] or [signup] to identify.")

  def run = Future { read }

  def read : Unit = {
    val s = StdIn.readLine
    val r = parse (action, s) match {
      case Success(m,_) => {
	handler (m)
	read
      }
      case Failure (msg,_) =>
        println("Malformed command, please enter a valid command or type help.")
        read
      case Error (msg,_) =>
        println("An error occured.")
        read
    }
  }
}

package client.supervisor

import akka.actor.{ Actor, Props }
import client.com.NetClientFactory
import client.com.NetClientFactory.NetClient
import client.io.output.OutputManager
import client.io.output.OutputMsg._
import client.io.input.InputMsg._
import scala.concurrent.Future
import ext.MyFuture._
import common._

import net.message.{
  Login => LoginMsg,
  GetLocation => GetLocationMsg,
  SignUp => SignUpMsg,
  Logout => LogoutMsg,
  GetPokemons => PokemonsMsg,
  GetItems => ItemsMsg,
  MoveTo => MoveToMsg,
  MoveUp => MoveUpMsg,
  MoveDown => MoveDownMsg,
  MoveLeft => MoveLeftMsg,
  MoveRight => MoveRightMsg,
  Check => CheckMsg,
  Capture => CaptureMsg,
  DigItem => DigItemMsg,
  IsAbleToPlay
}


class Supervisor(val net : NetClient) extends Actor {
  import context._

  val output = system.actorOf(Props[OutputManager], "outputManager")

  def whenloggedout : Receive = {

    case GetHelp => output ! Help

    case Login(u,p) => {
      val success = (id : Long) => {
	output ! LoginSuccess
	context become loggedin
      }
      net request (LoginMsg(u,p)) >|= (
	(_ : Option[Long]) map success getOrElse (output ! LoginFail)
      )
    }

    case SignUp(u,p) => {
      net request (SignUpMsg(u,p)) ?|= (
	output ! SignUpSuccess,
	output ! SignUpFail
      )
    }

    case Exit => {
      output ! ExitEvent
      Future {
	NetClientFactory.shutdown
	(context system) terminate
      }
    }

    case _ =>
      output ! InfoText("Please login or signup to enter the adventure.")
      whenloggedout (GetHelp)

  }

  def whenloggedin : Receive = {

    case Login  (_,_) => output ! InfoText("Already logged in.")
    case SignUp (_,_) => output ! InfoText("Logout before signing up.")
    case GetHelp      => output ! Help

    case Logout =>
      net send LogoutMsg
      output ! LogoutEvent
      context become loggedout

    case Exit => 
      net send LogoutMsg
      output ! ExitEvent
      Future {
	NetClientFactory.shutdown
	(context system) terminate
      }

    case GetPokemons => 
      net request PokemonsMsg >|= ((pokemons : Seq[CapturedPokemon]) =>
	output ! Pokemons(pokemons)
      )

    case GetItems => 
      net request ItemsMsg >|= ((items : Seq[OwnedItem]) =>
	output ! Items(items)
      )

    case GetLocation =>
      net request GetLocationMsg >|= ((t:(Double,Double)) =>
        output ! ActualLocation(t._1,t._2)
      )

    case Check =>
      net request CheckMsg >|= (
	((cnt : (Seq[GenItem],Option[GenPokemon])) => {
	  val msg_pk = cnt._2 match {
	    case Some (gp) => "There is a Pokemon here !"
	    case None => "No Pokemon here."
	  }
	  val msg_item = cnt._1 match {
	    case Nil => "No items here."
            case _ => "There are items here !"
	  }
	  output ! InfoText (msg_pk)
          output ! InfoText (msg_item)
	}
      ))

    case Capture =>
      net request IsAbleToPlay ?|= (
	(net request CheckMsg >|= (
	  ((cnt : (Seq[GenItem],Option[GenPokemon])) => {
	    cnt._2 map (gp => {
	      net send CaptureMsg(gp id)
	      output ! InfoText ("Pokemon captured ! Check that with [pokemons] ^_^ ")
	    }) getOrElse (output ! InfoText ("There is no pokemon to capture here."))
	  }))), (
          output ! InfoText ("Not enough balls."))
      )


    case DigItem =>
      net request CheckMsg >|= (
	((cnt : (Seq[GenItem],Option[GenPokemon])) => {
          cnt._1 match {
            case Nil => output ! InfoText ("You are diggin' for nothing ! *idiot*")
            case items => {
              items foreach (item => net send DigItemMsg(item.id))
              output ! InfoText ("New items in your bag ! Check that with [items] ^_^ ")
            }
          }
	})
      )

    case msg @ MoveUp => 
      net send MoveUpMsg
      output ! MoveUpMsg

    case msg @ MoveDown =>
      net send MoveDownMsg
      output ! MoveDownMsg

    case msg @ MoveLeft =>
      net send MoveLeftMsg
      output ! MoveLeftMsg

    case msg @ MoveRight =>
      net send MoveRightMsg
      output ! MoveRightMsg

    case msg @ Location(x,y) =>
      net send MoveToMsg(x,y)
  }

  def loggedin = whenloggedin
  def loggedout = whenloggedout

  def receive = loggedout
}

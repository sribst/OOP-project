package server
import akka.actor.{ Actor, ActorRef, Props }
import scala.concurrent.Future
import net.message.{Position, Positions}

class DefaultActor extends Actor {
  import context._
  val looptimer = 10000
  private case object DoSth

  var positions : List[(Double,Double)] = List ()

  val token_manager = context.actorOf(Props(TokenMaker))

  val loopf = Future { loop }

  override def postStop = loopf failed

  def receive = {
    case DoSth => {
      val poslist = positions
      positions = List ()
      println ("doing something")
      Future {
	val pl = Positions(poslist map (tup => (tup _1, tup _2)))
	token_manager ! pl
      }
    }
    case Position(x, y) => {
      positions = (x, y) :: positions
      println ("position " + x + " " + y)
    }
    case _ => Future {println ("Server :: Received unknown message")}
  }

  def loop : Unit = {
    Thread.sleep(looptimer)
    self ! DoSth
    loop
  }
}

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._

case class MoveTo(x : Int, y : Int)
case object Show

class Map extends Actor {

  var player = (0,0)

  var map = {
    var m = new Array[Array[Char]] (10)
    for (i <- 0 to 9)
      m (i) = new Array[Char] (10)
    m
  }
  
  
  def receive = {
    case Move(x,y) => {
      map (player _1)(player _2) = ' '
      player = (x,y)
      map(x)(y) = 'P'
      print ("MOVE")
    }
    case Show => {
      print ("Hop")
    }
  }

}

object TestAkka extends App {
    
  val system = ActorSystem("mapmover")
  val map = system.actorOf(Props[Map], "map")
  val inbox = Inbox.create(system)
  map.tell(MoveTo(1,1), ActorRef.noSender)
  inbox.send(map, Show)
  val schd = system.scheduler.schedule(0.seconds, 1.seconds, map, Show) (system.dispatcher, map)

}


package server
import akka.actor._
import net.message._

object TokenMaker extends Actor {
  private var positions: List[(Double,Double)] = List()

  val MAX = 5


  def points_close(pos1: (Double,Double), pos2: (Double,Double))= {
    def distance(n: Double, m: Double) = (n - m) abs
    val abs = distance(pos1 _1,pos2 _1)
    val ord = distance(pos1 _2, pos2 _2)
    (abs < MAX) && (ord < MAX)
  }

  def filter_close(list: List[(Double,Double)], pos: List[(Double,Double)]): List[(Double,Double)] = {
    def f(elem: (Double,Double), rep: List[(Double,Double)])
        : Boolean= {
      rep match {
        case h::t if points_close(elem, h) => true
        case h::t => f(elem, t)
        case Nil => false
      }
    }
    list match {
      case h::t if !f(h, pos) => filter_close(t, h::pos)
      case h::t => filter_close(t, pos)
      case Nil => pos
    }
  }


  def receive = {
    case Positions(list) =>{
      positions = filter_close(list,List())
     context.actorOf(Props[TokenManager]) ! Positions(positions)
    }
    case  _ => println("Unknown message in TokenMaker")
  }
}

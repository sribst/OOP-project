import akka.actor._

object PartialSnapshot extends App{
  val system = ActorSystem("PartialSnapshot")
  val authority = system.actorOf(Props[Authority],name="Authority")
}

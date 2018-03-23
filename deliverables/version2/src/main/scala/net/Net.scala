package net

import akka.actor._
import com.typesafe.config.ConfigFactory
import scala.reflect.ClassTag
import scala.concurrent.Future
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global
import ext.MyFuture._
import ext.ImplicitTimeout
import net.message._

abstract class Net(systemName : String, configName : String)
    extends ImplicitTimeout {

  val timeout = 10

  val system = ActorSystem(
    systemName,
    ConfigFactory.load(configName)
  )

  def shutdown = system.terminate
}

class ConnectionHandler[Handler <: Actor : ClassTag, Default <: Actor : ClassTag] extends Actor
    with ImplicitTimeout {
  import context._
  val timeout = 5
  val default = context.actorOf(Props[Default])
  override def receive : Receive = {
    case Connect => {
      val handler = context.actorOf(Props[Handler])
      sender ! handler
    }
    case Broadcast (m) => {
      val res = liftSeq (children map (_ ? m) toSeq)
      res >|= (_ foreach (println(_)))
    }
    case msg => default tell (msg, sender)
  }
}

object ConnectionHandler {
  def props[H <: Actor : ClassTag, D <: Actor : ClassTag] = Props(new ConnectionHandler[H,D] ())
}

abstract class ServerFactory (config : String) extends Net (
  "NetServerSystem",
  config
) {

  def apply[ServerActor <: Actor : ClassTag, Default <: Actor : ClassTag] (name : String) = {
    val serverActor = system.actorOf(ConnectionHandler.props[ServerActor, Default],name)
  }
}

class ClientActor (val serverContext : String) extends Actor
    with ImplicitTimeout {
  val timeout = 10
  val serverActor = {
    val connectionActor = context.actorSelection(serverContext);
    (connectionActor ? Connect).mapTo[ActorRef]
  }
  override def postStop = {
    serverActor >|= (_ ! PoisonPill)
  }
  override def receive: Receive = {
    case Send (msg) =>
      serverActor >|= (_ ! msg)
    case Request(msg) => {
      val s = sender
      val f = serverActor >|= (_ ? msg)
      f >|= (s ! _)
    }
    case _ => Future { println("Received unknown msg ") }
  }
}

object ClientActor {
  def props (sc : String) = new ClientActor(sc)
}

abstract class ClientFactory (config : String) extends Net (
  "NetClientSystem",
  config
) {

  class NetClient (clientActor : ActorRef) {

    def send (a : Any) = clientActor ! (Send (a))

    def request[T : ClassTag] (a : Any) : Future[T] =
      (clientActor ? (Request (a))).mapTo[Future[T]] >>= (f => f)

    def stop = clientActor ! PoisonPill

  }

  type str = String

  def apply (ip : str, port : str, target : str, name : str) : NetClient = {
    val sc = "akka.tcp://NetServerSystem@" + ip + ":" + port + "/user/" + target
    val clientActor = system.actorOf(Props (ClientActor.props (sc)), name)
    new NetClient (clientActor)
  }

}

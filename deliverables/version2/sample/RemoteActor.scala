//import java.io.File
import akka.actor._
import com.typesafe.config.ConfigFactory

/**
 * Remote actor which listens on port 5150
 */
class RemoteActor extends Actor {

  override def receive: Receive = {

    case msg : String => {
      println("remote received " + msg + " from " + sender)
      sender ! "hi from remote"
    }

    case _ => println("Received unknown msg ")

  }
}

object RemoteActorTest {
  def main(args: Array[String]) {
/*
    //get the configuration file from classpath
    val configFile = getClass.getClassLoader.getResource("remote_application.conf").getFile
    //parse the config
    val config = ConfigFactory.parseFile(new File(configFile))
    //create an actor system with that config
    val system = ActorSystem("RemoteSystem" , config)
    * */
    //create an actor system with the config
    val system = ActorSystem(
      "RemoteSystem",
      ConfigFactory.load("remote_application.conf")
    )
    //create a remote actor from actorSystem
    val remote = system.actorOf(Props[RemoteActor], name="remote")
    println("remote is ready")


  }
}
*/

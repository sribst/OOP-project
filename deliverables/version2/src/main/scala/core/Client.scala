import akka.actor._

class Player extends Actor{

  var position = 0

  def receive = {
    case GetClientPosition => {
      position += 1
      sender ! ClientPosition(position)
    }
  }
}

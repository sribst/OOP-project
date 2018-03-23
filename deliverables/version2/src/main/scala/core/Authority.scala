import akka.actor._

class Authority extends Actor{

  var positions: List[Int] = Nil

  val player = context.actorOf(Props[Player],name="player")
  

  run(10)

  def printPositions(pos: List[Int]): Unit= {
    print("[")
    pos.foreach(p => print(p + ";"))
    println("]")
  }

  def receive = {
    case ClientPosition(newPos) => {
      positions = newPos::positions
      printPositions(positions)
    }
  }

  def run(times: Int) = {
    for( i <- 0 until times)
      player ! GetClientPosition
  }

}

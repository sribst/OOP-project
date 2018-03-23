import java.net._

object Server extends App{
  val s = new ServerSocket(9999)
  var count = 1
  while (count < 3){
    var bus = new BusTCP (socket = s.accept())
    var message = bus.recv()
    bus.send(message)
    count += 1
  }
}


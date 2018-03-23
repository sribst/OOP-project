class Authority extends AbstractServer {

  val dbAddress = "some_address:some_port"

  val defaultTimeout = 15000

  val authPort = 9999

  setPort(authPort)

  var continue = true

  while (continue) {
    val bus = accept(defaultTimeout)
    val message = bus.recv
    bus.send (message)
    continue = false
  }

}

object Authority{
  def apply () = new Authority
}

object Auth extends App {
  Authority ()
}


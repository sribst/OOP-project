object Client extends App{
  val bus = new BusTCP(address = "localhost:9999")
  bus.send(Message ("SIGN:colin:niloc"))
  println(bus.recv())
}

import java.net._

trait Serverizable{

  val host = new ServerSocket

  def setPort (port: Int) =
    host.bind(new InetSocketAddress("localhost", port))


  def accept () :Bus =
    new BusTCP (socket = host.accept())

  def accept (timeo: Int)=
    new BusTCP (socket = host.accept(), timeout = timeo)
}

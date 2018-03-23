import java.net._
import java.io._
import java.util._

class BusTCP (
  socket :Socket = new Socket,
  address :String = "none",
  timeout: Int = 90000) extends Bus {
  require (address != null)
  require (timeout > 0 && timeout <= 90000)
  
  lazy private val ADDRESSDELIMITER = ":"

  socket.setSoTimeout(timeout)
  socket.setKeepAlive(true)

  private def parseAddress(addr :String) = {
    if(!addr.equals("none")){
      val stok = new StringTokenizer (addr, ADDRESSDELIMITER)
      val inetAddr = InetAddress.getByName(stok.nextToken)
      val port = Integer.parseInt(stok.nextToken)
      (inetAddr, port)
    }
    else
      (socket.getInetAddress, socket.getPort)
  }
  

  lazy val addrPort = parseAddress (address)

  private def connect = {
    if(!socket.isConnected)
      socket.connect(
        new InetSocketAddress(addrPort._1, addrPort._2),timeout)
  }

  if (!socket.isConnected)
    this.connect

  lazy val bufferedReader = new BufferedReader(
    new InputStreamReader(socket.getInputStream))


  lazy val printWriter = new PrintWriter(
    new OutputStreamWriter(socket.getOutputStream))

  def disconnect = {
    socket.close
    printWriter.close
    bufferedReader.close
  }

  def send (message : Msg) = {
    printWriter.println(message)
    printWriter.flush
  }
  
  def recv() : Msg = {
    try
      Message(bufferedReader.readLine)
    catch{
      case ste: SocketTimeoutException => throw new Exception
    }
  }

}

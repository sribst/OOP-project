package client.com

import net.{ ClientFactory => CF }

object NetClientFactory extends CF("client_application.conf") {

  def apply () : NetClient =
    NetClientFactory ("127.0.0.1", "5150", "server", "netClient")
}



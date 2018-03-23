package ext

import scala.concurrent.duration._
import akka.util.Timeout

trait ImplicitTimeout { self : { val timeout : Int } =>
  lazy implicit val itimeout = Timeout (timeout seconds)
  lazy implicit val iduration = itimeout duration
}

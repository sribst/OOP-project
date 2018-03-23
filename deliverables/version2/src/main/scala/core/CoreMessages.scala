sealed trait CoreMessages
case object GetClientPosition extends CoreMessages
case class ClientPosition(position: Int) extends CoreMessages

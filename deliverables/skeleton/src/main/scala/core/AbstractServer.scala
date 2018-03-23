abstract class AbstractServer extends Serverizable{

  def validate (passphrase :String) : Boolean = {
    true
  } 

  def req_auth (passphrase :String, bus : Bus) : Boolean = {
    bus.send (Message (passphrase))
    ((bus.recv).equals("OK"))
  }


  def validate_auth (bus : Bus) = {
    val passphrase = bus.recv
    val valid = validate (passphrase.toString)
    valid match {
      case true => bus.send (Message ("OK"))
      case false => bus.send (Message ("KO"))
        bus.disconnect
    }
  }

}




/*abstract sealed class Msg
case class Sign(username : String, password : String)
    extends Msg
case class Siok(uid : Integer) extends Msg
case class Sino() extends Msg
case class Cacc(username : String, password : String)
    extends Msg
case class Caok() extends Msg
case class Cano() extends Msg
case class Geti(id : Integer) extends Msg
case class Ilen(size : Integer) extends Msg
case class Item(item : Any) extends Msg
case class Itok() extends Msg
case class Getc(id : Integer) extends Msg
case class Clen(size : Integer) extends Msg
case class Coll(pokemon : Any) extends Msg
case class Cook() extends Msg
case class Move(x : Integer, y : Integer) extends Msg
case class Mook() extends Msg
case class Mono() extends Msg
case class Ipoi(idpoi : Integer) extends Msg
case class Poia() extends Msg
case class Ipok(x : Integer, y : Integer) extends Msg
case class Poka() extends Msg

object Message {
  def stringToMessage(msg : String) : Msg = {
    msg.substring(0,4) match
    {
      case "SIGN" =>
        val username = msg.substring(4).split(":")(0)
        val password = msg.substring(4).split(":")(1)
        return Sign(username,password)
      case "SIOK" =>
        return Siok(Integer.parseInt(msg.substring(4)))
      case "SINO" => return Sino()
      case "CACC" =>
        val username = msg.substring(4).split(":")(0)
        val password = msg.substring(4).split(":")(1)
        return Cacc(username,password)
      case "CAOK" => return Caok()
      case "CANO" => return Cano()
      case "GETI" =>
        val id = Integer.parseInt(msg.substring(4))
        return Geti(id)
      case "ILEN" =>
        val size = Integer.parseInt(msg.substring(4))
        return Ilen(size)
      case "ITEM" =>
        val item = msg.substring(4)
        return Item(item)
      case "ITOK" => return Itok()
      case "GETC" =>
        val id = Integer.parseInt(msg.substring(4))
        return Getc(id)
      case "CLEN" =>
        val size = Integer.parseInt(msg.substring(4))
        return Clen(size)
      case "COLL" =>
        val pokemon = msg.substring(4)
        return Coll(pokemon)
      case "COOK" => return Cook()
      case "MOVE" =>
        val x = Integer.parseInt(msg.substring(4).split(" ")(0))
        val y = Integer.parseInt(msg.substring(4).split(" ")(1))
        return Move(x,y)
      case "MOOK" => return Mook()
      case "MONO" => return Mono()
      case "IPOI" =>
        val idpoi = Integer.parseInt(msg.substring(4))
        return Ipoi(idpoi)
      case "POIA" => return Poia()
      case "IPOK" =>
        val x = Integer.parseInt(msg.substring(4).split(" ")(0))
        val y = Integer.parseInt(msg.substring(4).split(" ")(1))
        return Ipok(x,y)
      case "POKA" => return Poka()
      case _ => return null
    }
  }

  def messageToString(msg : Msg) : String = {
    msg match
    {
      case Sign(username,password) =>
        return "SIGN"+username+":"+password
      case Siok(uid) => return "SIOK"
      case Sino() => return "SINO"
      case Cacc(username,password) =>
        return "CACC"+username+":"+password
      case Caok() => return "CAOK"
      case Cano() => return "CANO"
      case Geti(uid) => return "GETI"+uid
      case Ilen(size) => return "ILEN"+size
      case Item(item) => return "ITEM"+item
      case Itok() => return "ITOK"
      case Getc(uid) => return "GETC"+uid
      case Clen(size) => return "CLEN"+size
      case Coll(pokemon) => return "COLL"+pokemon
      case Cook() => return "COOK"
      case Move(x,y) => return "MOVE"+x+" "+y
      case Mook() => return "MOOK"
      case Mono() => return "MONO"
      case Ipoi(idpoi) => return "IPOI"+idpoi
      case Poia() => return "POIA"
      case Ipok(x,y) => return "IPOK"+x+" "+y
      case Poka() => return "POKA"
      case _ => return ""
    }
  }

  def apply (_message :String) = stringToMessage(_message)

  def getInfos(msg : Msg) : (Option[Any], Option[Any]) = {
    msg match {
     case Sign(username,password) => return (Some(username), Some(password))
     case Cacc(username,password) => return (Some(username), Some(password))
     case Siok(id) => return (Some(id),None)
     case Geti(id) => return (Some(id),None)
     case Getc(id) => return (Some(id),None)
     case Move(x,y) => return (Some(x), Some(y))
     case Ipoi(id) => return (Some(id), None)
     case Ipok(x,y) => return (Some(x),Some(y))
     case _ => return (None, None)
    }
  }
   
  def handleMessage(b : Bus, msg : Msg) {
    msg match {
      case Sign(username,password) =>
        //Connection process... NYI
        val uid = 0
        b.send(Siok(0))
      case Cacc(username,password) =>
        //Creation process... NYI
        b.send(Caok())
      case Geti(id) =>
      //Do something
      case Itok() =>
      //Do something
      case Getc(id) =>
      //Do something
      case Cook() =>
      //Do something
      case Move(x,y) =>
      //Do something
      case Ipoi(idpoi) =>
      //Do something
      case Ipok(x,y) =>
      //Do something
      case Siok(uid) =>
	println("You are connected!")
      case Sino() =>
  	println("Connection denied : this account doesn't exist.")
      case Caok() =>
  	println("Your account has been created.")
      case Cano() =>
  	println("This account already exists.")
      case Ilen(size) =>
      //Do something
      case Item(item) =>
  	//Do something
  	b.send(Itok())
      case Clen(size) =>
      //Do something
      case Coll(pokemon) =>
  	//Do something
  	b.send(Cook())
      case Mook() =>
      //Do something
      case Mono() =>
      //Do something
      case Poia() =>
      //Do something
      case Poka() =>
      //Do something
      case _ =>
  	println("Unknown or empty (result of an error) message.")
    }
  }
}
*/
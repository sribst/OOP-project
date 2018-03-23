sealed abstract class Msg {
  def string : String
  override def toString = string
}

case class Sign (username : String, password : String) extends Msg {
  def string = "SIGN" + username + ":" + password
}
case class Siok (uid : Integer) extends Msg {
  def string = "SIOK"
}
case object Sino extends Msg {
  def string = "SINO"
}
case class Cacc (username : String, password : String) extends Msg {
  def string = "CACC" + username + ":" + password
}
case object Caok extends Msg {
  def string = "CAOK"
}
case object Cano extends Msg {
  def string = "CANO"
}
case class Geti (id : Integer) extends Msg {
  def string = "GETI" + id
}
case class Ilen (size : Integer) extends Msg {
  def string = "ILEN" + size
}
case class Item (item : Any) extends Msg {
  def string = "ITEM" + item
}
case object Itok extends Msg {
  def string = "ITOK"
}
case class Getc (id : Integer) extends Msg {
  def string = "GETC" + id
}
case class Clen (size : Integer) extends Msg {
  def string = "CLEN" + size
}
case class Coll (pokemon : Any) extends Msg {
  def string = "COLL" + pokemon
}
case object Cook extends Msg {
  def string = "COOK"
}
case class Move (x : Integer, y : Integer) extends Msg {
  def string = "MOVE" + x + " " + y
}
case object Mook extends Msg {
  def string = "MOOK"
}
case object Mono extends Msg {
  def string = "MONO"
}
case class Ipoi (idpoi : Integer) extends Msg {
  def string = "IPOI" + idpoi
}
case object Poia extends Msg {
  def string = "POIA"
}
case class Ipok (x : Integer, y : Integer) extends Msg {
  def string = "IPOK" + x + " " + y
}
case object Poka extends Msg {
  def string = "POKA"
}


object Message {

  object InvalidMsgString extends Throwable

  private implicit def parse (s : String) : Integer = Integer parseInt s

  def apply (s : String) = stringToMsg (s)

  def apply (msg : Msg) = msg toString

  def split (s : String, sep : String) =
    ((arr : Array[String]) => (arr(0), arr(1))) (s split sep)

  def stringToMsg (msg : String) = {
    try {
      val msg_code = msg.substring(0,4)
      val msg_content = msg.substring(4)
      msg_code match {
        case "SIGN" => {
          val (username, password) = split (msg_content, ":")
          Sign (username, password)
        }
        case "SIOK" => Siok (msg_content)
        case "SINO" => Sino
        case "CACC" => {
          val (username, password) = split (msg_content, ":")
          Cacc (username, password)
        }
        case "CAOK" => Caok
        case "CANO" => Cano
        case "GETI" => Geti (msg_content)
        case "ILEN" => Ilen (msg_content)
        case "ITEM" => Item (msg_content)
        case "ITOK" => Itok
        case "GETC" => Getc (msg_content)
        case "CLEN" => Clen (msg_content)
        case "COLL" => Coll(msg_content)
        case "COOK" => Cook
        case "MOVE" => {
          val (x,y) = split (msg_content, " ")
	  Move (x,y)
        }
        case "MOOK" => Mook
        case "MONO" => Mono
        case "IPOI" => Ipoi(msg_content)
        case "POIA" => Poia
        case "IPOK" => {
	  val (x,y) = split (msg_content, " ")
          Ipok (x,y)
        }
        case "POKA" => Poka
      }
    }
    catch {
      case _ : Throwable => throw InvalidMsgString
    }
  }

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

  def handleMsg (b : Bus, msg : Msg) {
    msg match {
      case Sign(username,password) =>
        //Checks if user exists in the db and if the pw is correct
        if (UserDB.user_exists(username) &&
            UserDB.is_password (username) (password)) {
          b.send(Siok(0)) //Wrong id, Long or Integer ?
        }
        else b.send(Sino)
      case Cacc(username,password) =>
        //Checks if user isn't already in the db
        try UserDB.user_add(username, password)
        catch {
          case _ : UserDB.UserAlreadyExists => b.send(Cano)
	}
        b.send(Caok)
      case Geti(id) =>
      //Do something
      case Itok =>
      //Do something
      case Getc(id) =>
      //Do something
      case Cook =>
      //Do something
      case Move(x,y) =>
      //Do something
      case Ipoi(idpoi) =>
      //Do something
      case Ipok(x,y) =>
      //Do something
      case Siok(uid) =>
        println("You are connected!")
      case Sino =>
        println("Connection denied : this account doesn't exist.")
      case Caok =>
        println("Your account has been created.")
      case Cano =>
        println("This account already exists.")
      case Ilen(size) =>
      //Do something 
      case Item(item) =>
      //Do something
      //b.send(Itok)
      case Clen(size) =>
      //Do something
      case Coll(pokemon) =>
      //Do something
      //b.send(Cook)
      case Mook =>
      //Do something
      case Mono =>
      //Do something
      case Poia =>
      //Do something
      case Poka =>
      //Do something
    }
  }
}

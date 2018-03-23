abstract trait Client2 {

  val bus_srv = new BusTCP(address = "82.245.152.43:35000")
  val bus_db  = new BusTCP(address = "82.245.152.43:45000")
  var userID:Int
  var creatureCollection:TokenCollection
  var itemCollection:TokenCollection

  object WrongResponse extends Exception
  def resp_srv(msg:Msg):Any = {
    bus_srv.send(msg)
    (msg,bus_srv.recv()) match {
      case (Cacc(username,password),Caok) => true
      case (Cacc(username,password),Cano) => false
      case (Move(x,y),Mook) => true
      case (Move(x,y),Mono) => false
      case (Sett(token),Seto) => true
      case (Sett(token),Setn) => false
      case _ => throw new WrongResponse
    }
  }

  def resp_db(msg:Msg):Any = {
    bus_db.send(msg)
    (msg,bus_db.recv()) match {
      case (Sign(username,password),Siok(uid)) => uid
      case (Sign(username,password),Sino) => false
      case (Gett(id),Toke(token)) => token
      case (_,Cols(size)) =>
        def getTokCol(i:Int,acc:TokenCollection[T]):TokenCollection[T] = {
          if(i > 0) {
            bus_db.recv() match {
              case Toke(token) =>
                acc.add(token)
                getTokCol(i-1,acc)
              case _ => throw new WrongResponse
            }
          }
          else
            return acc
        }
        msg match {
          case Getu(tokenType,uid) => getTokCol(size,new TokenCollection[tokenType])
          case geta(tokenType,gps) => getTokCol(size,new TokenCollection[tokenType])
          case _ => throw new Exception
        }
      case _ => throw new WrongResponse
    }
  }

  def connect(username:String,password:String)={
    resp_db(Sign(username,password)) match {
      case uid:Int   => this.userID = uid
      case b:Boolean => b
      case _       => throw new Exception
    }
  }

  // def getToken[TokenType] = {
  //   resp_db(getToken[TokenType](this.userID) match {
  //     case token:Token => 
  //   }
  // }

  // def getCreatureCollection = {
  //   resp_db(Getc(this.userID)) match {
  //     case tokenCol:TokenCollection => this.creatureCollection = tokenCol
  //     case _ => throw new Exception
  //   }
  // }

  // def getItemCollection = {
  //   resp_db(Geti(this.userID)) match {
  //     case tokenCol:TokenCollection => this.itemCollection = tokenCol
  //     case _ => throw new Exception
  //   }
  // }

  // def getToken(tid:Int) : Token = {
  //   resp_db(Gett(tid)) match {
  //     case token:Token => token
  //     case _ => throw new Exception
  //   }
  // }

  // def setToken(token:Token) = {
  //   resp_srv(Sett(token)) match{
  //     case b:Boolean => b
  //     case _    => throw new Exception
  //   }
  // }

  // def getTokenColl(tokenType:Token) = {
  //   resp_db(Getc(tokenType,this.userID)) match{
  //     case tokCol:TokenCollection => tokCol
  //     case _ => throw new Exception
  //   }
  // }

  // def getTokenArea(tokenType:Token,gps:Location) = {
  //   resp_db(Geta(tokenType,gps)) match{
  //     case tokCol:TokenCollection => tokCol
  //     case _ => throw new Exception
  //   }
  // }

}

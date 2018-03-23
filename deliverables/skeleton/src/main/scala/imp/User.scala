
class Player (
  val id : Int,
  val name : String,
  val owner : Int,
  var creatures : List[CapturedPokemon],
  var items : List[CollectedItem]
) extends IPlayer

class UserInfo (
  val id : Int,
  val name : String
) extends IUser

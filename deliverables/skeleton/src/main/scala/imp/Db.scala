
class Store[O <: Identified[Int]] {

  private var store = Map[Int, O] ()
  private var i = 0

  def add (o : O) = {
    store += (i -> o)
    i += 1
    i
  }
  def get (id : Int) = store (id)
  def remove (id : Int) = store = store.filter (_._1 != id)
  def update (o : O) = store += (o.id -> o)
  def filter (p : O => Boolean) =
    (store filter (tup =>
      p (tup._2)) toList) map (_._2)
}

object Db extends IDbFactory {

  abstract class FakeTable[I, O<:Identified[Int]] extends ITable[I, O] {
    
    protected var s : Store[O] = new Store[O] ()

    def add (obj : O) = s add obj

    def remove (obj : O) = s remove obj.id
    
    def update (obj : O) = s update obj

  }

  object FakePointOfInterestTable extends FakeTable[D2Located, PointOfInterest]{
    private var range = new D2Coordinates (10,10)
    def get (l : D2Located) = {
      val c = l.getCoordinates
      s.filter (poi => poi.getCoordinates < c + range &&
		poi.getCoordinates > c - range)
    }
  }

  object FakePlayerCreaturesTable extends FakeTable[Int, CapturedPokemon] {
    def get (id : Int) = s.filter (pok => pok.owner == id)
  }

  object FakeCreaturesTable extends FakeTable[Int, Pokemon] {
    def get (id : Int) = s.filter (pok => pok.id == id)
  }

  object FakePlayerItemsTable extends FakeTable[Int, CollectedItem] {
    def get (id : Int) = s.filter (it => it.owner == id)
  }

  object FakeItemsTable extends FakeTable[Int, BaseItem[_]] {
    def get (id : Int) = s.filter (it => it.id == id)
  }

  object FakePlayersTable extends FakeTable[Int, IPlayer] {
    def get (id : Int) = s.filter (player => player.id == id)
  }

  object FakeUsersTable extends FakeTable[Int, IUser] {
    def get (id : Int) = s.filter (user => user.id == id)
  }

  object FakeDb extends IDb with ConnexionVerif {

    type playerId = Int
    type creatureId = Int
    type itemId = Int
    type userId = Int

    type located        = D2Located
    type location       = PointOfInterest
    type playerCreature = CapturedPokemon
    type creature       = Pokemon
    type playerItem     = CollectedItem
    type item           = BaseItem[_]
    type player         = IPlayer
    type user           = IUser

    type LocationTable        = FakePointOfInterestTable.type
    type PlayerCreaturesTable = FakePlayerCreaturesTable.type
    type PlayerItemsTable     = FakePlayerItemsTable.type
    type CreaturesTable       = FakeCreaturesTable.type
    type ItemsTable           = FakeItemsTable.type
    type PlayersTable         = FakePlayersTable.type
    type UsersTable           = FakeUsersTable.type

    def players         = FakePlayersTable
    def users           = FakeUsersTable
    def locations       = FakePointOfInterestTable
    def playerCreatures = FakePlayerCreaturesTable
    def playerItems     = FakePlayerItemsTable
    def creatures       = FakeCreaturesTable
    def items           = FakeItemsTable


    type username = String
    type password = String
    def verifyLogin (u : username, p : password) = 1

  }

  type Db = IDb

  def getDb = FakeDb

}


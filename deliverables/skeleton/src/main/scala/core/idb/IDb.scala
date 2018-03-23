
abstract trait IDb {

  type playerId
  type creatureId
  type itemId
  type userId

  type located         <: Located

  type location       <: Location
  type playerCreature <: CapturedCreature
  type creature       <: Creature
  type playerItem     <: Collected
  type item           <: Collectable
  type player         <: IPlayer
  type user           <: IUser

  type UsersTable           <: ITable [userId, user]
  type PlayersTable         <: ITable [playerId, player]
  type LocationTable        <: ITable [located, location]
  type PlayerCreaturesTable <: ITable [playerId, playerCreature]
  type PlayerItemsTable     <: ITable [playerId, playerItem]
  type CreaturesTable       <: ITable [creatureId, creature]
  type ItemsTable           <: ITable [itemId, item]

  def users     : UsersTable
  def players   : PlayersTable
  def locations : LocationTable
  def playerCreatures : PlayerCreaturesTable
  def playerItems : PlayerItemsTable
  def creatures : CreaturesTable
  def items : ItemsTable

}

abstract trait IDbFactory {

  type Db <: IDb

  def getDb : Db

}

abstract trait ConnexionVerif { self : IDb =>
  type username
  type password
  def verifyLogin (u : username, p : password) : playerId
}

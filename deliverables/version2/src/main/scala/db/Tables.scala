package db
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

// Waiting for core ?
final case class User (
  name     : String,
  password : String,
  location_x : Double,
  location_y : Double,
  id       : Long = 0L
)


// Schema for the USER table
final class USERDB (tag : Tag) extends Table[User] (tag, "USER") {

  def id = column[Long]("USER_ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("USER_NAME")

  def password = column[String]("USER_PWD")

  // add unique constraint on names
  def nameIndex = index("name_index", name, unique=true)

  def location_x = column[Double]("USER_X")

  def location_y = column[Double]("USER_Y")

  def * = (name,password,location_x,location_y,id) <> (User.tupled, User.unapply)
}



final case class Pokemon (
  name : String,
  id   : Long = 0L
)

final class POKEMONDB (tag : Tag) extends Table[Pokemon] (tag, "POKEMON") {

  def id = column[Long]("POKEMON_ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("POKEMON_NAME")

  // add unique constraint on names
  def nameIndex = index("name_index", name, unique=true)

  def * = (name, id) <> (Pokemon.tupled, Pokemon.unapply)
}

final case class GenPokemon (
  base_pokemon_id : Long,
  x : Double,
  y : Double,
  id   : Long = 0L
)

final class GENPOKEMONDB (tag : Tag) extends Table[GenPokemon] (tag, "GENPOKEMON") {

  def id = column[Long]("GENPOKEMON_ID", O.PrimaryKey, O.AutoInc)

  def base_pokemon_id = column[Long]("BASE_POKEMON_ID")

  def x = column[Double]("GENPOKEMON_X")

  def y = column[Double]("GENPOKEMON_Y")

  def * = (base_pokemon_id, x, y, id) <> (GenPokemon.tupled, GenPokemon.unapply)
}

final case class GenPokemonUsed (
  gen_id : Long,
  user_id : Long,
  timestamp : Long,
  id : Long = 0L
)

final class GENPOKEMONUSEDDB (tag : Tag) extends Table[GenPokemonUsed] (tag, "GENPOKEMONUSED") {

  def id = column[Long]("GENPOKEMONUSED_ID", O.PrimaryKey, O.AutoInc)

  def gen_id = column[Long]("GEN_POKEMON_ID")

  def user_id = column[Long]("USER_ID")

  def timestamp = column[Long]("TIME_USED")

  def * = (gen_id, user_id, timestamp, id) <> (GenPokemonUsed.tupled, GenPokemonUsed.unapply)
}


final case class CapturedPokemon (
  basePokemonId : Long,
  ownerId : Long,
  id   : Long = 0L
)

final class CAPTUREDPOKEMONDB (tag : Tag)
extends Table[CapturedPokemon] (tag, "CAPTUREDPOKEMON") {

  def id = column[Long]("CAPTUREDPOKEMON_ID", O.PrimaryKey, O.AutoInc)

  def basePokemonId = column[Long] ("BASEPOKEMON_ID")

  def ownerId = column[Long] ("OWNER_ID")

  def * = (basePokemonId,ownerId,id) <> (CapturedPokemon.tupled, CapturedPokemon.unapply)
}


final case class Item (
  name : String,
  ty   : Int,
  id   : Long = 0L
)

final class ITEMDB (tag : Tag) extends Table[Item] (tag, "ITEM") {

  def id = column[Long]("ITEM_ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("ITEM_NAME")

  def ty = column[Int]("ITEM_TYPE")

  def nameIndex = index("name_index", name, unique=true)

  def * = (name,ty,id) <> (Item.tupled, Item.unapply)
}


final case class OwnedItem (
  baseItemId : Long,
  ownerId : Long,
  count : Long,
  id : Long = 0L
)

final class OWNEDITEMDB (tag : Tag)
extends Table[OwnedItem] (tag, "OWNEDITEM") {

  def id = column[Long]("OWNEDITEM_ID", O.PrimaryKey, O.AutoInc)

  def baseItemId = column[Long] ("BASEITEM_ID")

  def ownerId = column[Long] ("OWNER_ID")

  def count = column[Long] ("COUNT")

  def * = (baseItemId,ownerId,count,id) <> (OwnedItem.tupled, OwnedItem.unapply)

}

final case class GenItem (
  base_item_id : Long,
  x : Double,
  y : Double,
  id   : Long = 0L
)

final class GENITEMDB (tag : Tag) extends Table[GenItem] (tag, "GENITEM") {

  def id = column[Long]("GENITEM_ID", O.PrimaryKey, O.AutoInc)

  def base_item_id = column[Long]("BASE_ITEM_ID")

  def x = column[Double]("GENITEM_X")

  def y = column[Double]("GENITEM_Y")

  def * = (base_item_id, x, y, id) <> (GenItem.tupled, GenItem.unapply)
}


final case class GenItemUsed (
  gen_id : Long,
  user_id : Long,
  timestamp : Long,
  id : Long = 0L
)

final class GENITEMUSEDDB (tag : Tag) extends Table[GenItemUsed] (tag, "GENITEMUSED") {

  def id = column[Long]("GENITEMUSED_ID", O.PrimaryKey, O.AutoInc)

  def gen_id = column[Long]("GEN_ITEM_ID")

  def user_id = column[Long]("USER_ID")

  def timestamp = column[Long]("TIME_USED")

  def * = (gen_id, user_id, timestamp, id) <> (GenItemUsed.tupled, GenItemUsed.unapply)
}

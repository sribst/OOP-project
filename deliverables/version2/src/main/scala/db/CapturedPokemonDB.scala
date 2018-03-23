package db
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import ext.MyFuture._

object CapturedPokemonDB extends DB {

  lazy val capturedDB = TableQuery[CAPTUREDPOKEMONDB]

  exec(capturedDB.schema.create)

  def captured (owner_id : Long) =
    exec (capturedDB.filter (p => p.ownerId === owner_id) result)

  def add_captured (base_id : Long, owner_id : Long) =
    exec (capturedDB += CapturedPokemon (base_id,owner_id))

}

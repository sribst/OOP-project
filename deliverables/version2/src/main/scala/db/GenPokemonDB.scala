package db
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import ext.MyFuture._

object GenPokemonDB extends DB {

  lazy val genPokemonDB = TableQuery[GENPOKEMONDB]

  exec(genPokemonDB.schema.create)

  val insertQuery = genPokemonDB returning genPokemonDB.map(_.id) into ((item, id) => item.copy(id = id))
  
  def create(base: Long, x: Double, y: Double) : Future[GenPokemon]= {
    val action = insertQuery += GenPokemon(base, x, y)
    exec(action)
  }

  def pokemon (id : Long) =
    exec (genPokemonDB.filter (p => p.id === id) result) >|= (_ head)

  def pokemon (x : Double, y : Double) =
    exec (genPokemonDB.filter (p => p.x === x && p.y === y) result) >|= (_ headOption)

  def add_pokemon (basePok : Long, x : Double, y : Double) =
    exec (genPokemonDB += GenPokemon (basePok, x, y))

  def delete (id : Long) = {
    val q = genPokemonDB.filter(_.id === id)
    val action = q.delete
    exec (action)
  }

}

package db
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import ext.MyFuture._

object PokemonDB extends DB {

  lazy val pokemonDB = TableQuery[POKEMONDB]

  exec(pokemonDB.schema.create)

  def pokemons =
    exec (pokemonDB.map(_.name).result)

  def pokemon (id : Long) =
    exec (pokemonDB.filter (p => p.id === id) result) >|= (_ head)

  def add_pokemon (name : String) =
    exec (pokemonDB += Pokemon (name))

  def nb_pokemon = exec (pokemonDB.length.result) 
}

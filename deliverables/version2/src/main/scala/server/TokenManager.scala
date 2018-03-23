package server
import akka.actor.Actor
import common._
import net.message._
import db.{ GenPokemon, GenPokemonDB, PokemonDB}
import ext.MyFuture._
import scala.concurrent.Future

class TokenManager extends Actor {

  private val COOLDOWN = 30000 //in miliseconds

  def new_token(pos: (Double,Double)): Future[Long] = {
    println(pos)
    // GenPokemonDB create (1L, pos _1, pos _2)) => _.id
    (PokemonDB nb_pokemon) >>=
      ((max:Int) =>
        GenPokemonDB create (Dice.rollLong(0L, max), pos._1, pos._2)
          >|= (_.id))


  }

  def waitCooldown(): Unit = {
    Thread.sleep(COOLDOWN)
  }

  def remove (tokens: List[Future[Long]]): Boolean = {
    tokens foreach (fl => fl >>= (id => GenPokemonDB delete id))
    true
  }

  def die(): Unit = {
    context.stop(self)
  }


  def receive = {
    case Positions(list) =>
      {
        val current_tokens = list.map(new_token)
        waitCooldown()
        remove(current_tokens) 
        die()
      }
    case _ => println("Unkown message in TokenManager")
  }

}



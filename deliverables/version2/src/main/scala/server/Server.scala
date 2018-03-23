package server
import akka.actor.{Actor, ActorRef, PoisonPill}
import net.{ ServerFactory => SF }
import db._
import scala.concurrent.Future
import net.message._
import ext.MyFuture._
import common.{
  Pokemon => CPokemon,
  CapturedPokemon => CCapturedPokemon,
  Item => CItem,
  ItemType,
  OwnedItem => COwnedItem,
  GenPokemon => CGenPokemon,
  GenItem => CGenItem,
  Dice
}
import scala.util.Try

class ServerActor extends Actor {
  import context._
  import DbConversion._

  var logged_as : Option[(String, Long)] = None


  def current_user_id = {
    val Some((_,id)) = logged_as
    id
  }

  var moving = false
  def onMove = {
    if (! moving) {
      moving = true
      UserDB user_location (current_user_id) >|= (tup =>
	parent ! Position(tup._1, tup._2)) >|= (_ => {
	  Thread.sleep (Dice.roll (10000, 60000)); moving = false
	})
    }
  }

  def receive = waitingForLogin

  def waitingForLogin : Receive = {

    case Login (u,p) => {
      val s = sender
      UserDB is_password (u,p) ?|= (
	{
	  UserDB user_id (u, p) >|= (id => {
	    logged_as = Some(u,id)
	    s ! Some (id)
	  })
	  context become loggedIn
	},
	s ! None
      )
    }

    case SignUp (u,p) => {
      val s = sender
      val f = UserDB add_user (u,p)
      f onComp ((_ => s ! true), (_ => s ! false))
    }

    case msg => parent ! msg

  }


  def loggedIn : Receive = {

    case Logout => {
      logged_as = None;
      context become waitingForLogin
    }

    case GetPokemons => {
      val s = sender
      CapturedPokemonDB captured (current_user_id) >|= (listcaptured => {
	println (listcaptured)
	val f = listcaptured map capturedDbToClient
	liftSeq (f) >|= (s ! _)
      })
    }

    case GetItems => {
      val s = sender
      OwnedItemDB all_owned (current_user_id) >|= (listowned => {
	println (listowned)
	val f = listowned map ownedDbToClient
	liftSeq (f) >|= (s ! _)
      })
    }

    case AddCapturedPokemon (id : Long) =>
      CapturedPokemonDB add_captured (id, current_user_id)

    case AddOwnedItem (id : Long, n : Long) =>
      OwnedItemDB add_owned (id, current_user_id, n)

    case MoveTo (x : Double, y : Double) =>
      UserDB user_update_location (current_user_id, x, y)

    case MoveUp =>
      UserDB user_move_up (current_user_id) >|= (_ => onMove)

    case MoveDown =>
      UserDB user_move_down (current_user_id) >|= (_ => onMove)

    case MoveLeft =>
      UserDB user_move_left (current_user_id) >|= (_ => onMove)

    case MoveRight =>
      UserDB user_move_right (current_user_id) >|= (_ => onMove)

    case GetLocation =>
      val s = sender
      UserDB user_location (current_user_id) >|= (s ! _)

    case Check => {
      val s = sender
      UserDB user_location (current_user_id) >>= (pos =>
        GenItemDB items (pos _1, pos _2) >|= (items => {
          val c_items = items map genItemDbToClient
          liftSeq (c_items) >|= (f_items => {
            GenPokemonDB pokemon (pos _1, pos _2) >|= (pok_o =>
	      pok_o map (pok => {
                GenPokemonUsedDB is_valid (pok id, current_user_id, 30L) ?|= (
	          genPokemonDbToClient (pok) >|= (c => s ! ((f_items,Some (c)))),
                  s ! ((f_items,None))
                )}) getOrElse (s ! ((f_items,None)))
            )
          })
        })
      )
    }

    case Capture (id : Long) => {
      OwnedItemDB consume_ball (current_user_id) >>= (_ =>
	GenPokemonDB pokemon (id) >>= (pok =>
	  GenPokemonUsedDB set_used (pok id, current_user_id) >>= (_ =>
            CapturedPokemonDB add_captured (pok base_pokemon_id, current_user_id))))
    }

    case IsAbleToPlay => {
      val s = sender
      OwnedItemDB has_ball current_user_id >|= (s ! _)
    }

    case DigItem (id : Long) => {
      GenItemDB item (id) >>= (item =>
        OwnedItemDB add_owned (item base_item_id,current_user_id,1L)
      )
    }

    case msg => parent ! msg

  }
}

object DbConversion {
  def pokemonDbToClient (p : Pokemon) = CPokemon (p name, p id)
  def capturedDbToClient (p : CapturedPokemon) =
    PokemonDB pokemon (p basePokemonId) >|= (basepokemon =>
      CCapturedPokemon (pokemonDbToClient (basepokemon)))
  def itemDbToClient (i : Item) = CItem (i name, ItemType (i ty), i id)
  def ownedDbToClient (i : OwnedItem) =
    ItemDB item (i baseItemId) >|= (baseitem =>
      COwnedItem (itemDbToClient (baseitem), i count))
  def genPokemonDbToClient (gp : GenPokemon) = {
    PokemonDB pokemon (gp base_pokemon_id) >|= (p =>
      CGenPokemon(pokemonDbToClient (p), gp id))
  }
  def genItemDbToClient (gi : GenItem) = {
    ItemDB item (gi base_item_id) >|= (p =>
      CGenItem(itemDbToClient (p), gi id))
  }
}

object ServerFactory extends SF("server_application.conf")

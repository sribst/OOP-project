package db
import scala.concurrent.Future
import scala.util.{ Success, Failure }
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import ext.MyFuture._

object UserDB extends DB {

  final case class UserAlreadyExists (user : String) extends Exception

  lazy val userDB = TableQuery[USERDB]

  exec(userDB.schema.create)

  def users =
    exec (userDB.map(_.name).result)

  def user_exists (name : String) =
    exec (userDB.filter(_.name === name).exists.result)

  def add_user (name : String, password : String) = user_exists (name) ?>= (
    throw new UserAlreadyExists (name),
    exec (userDB += User (name,password,0.0,0.0))
      //Default starting point on the map
  )

  def is_password (name : String, password : String) =
    exec (userDB.filter(
      u => u.name === name && u.password === password
    ).exists.result)

  def user_id (name : String, password : String) =
    exec (userDB.filter(
      u => u.name === name && u.password === password
    ).map (_.id).result) >|= (_.head)

  def user_update_password (user_id : Long, new_password : String) = {
    val query = userDB.filter(_.id === user_id).map(_.password)
    exec (query.update (new_password))
  }

  def user_x (user_id : Long) : Future[Double] = {
    val query_y = userDB.filter(_.id === user_id).map(_.location_y)
    exec (query_y.result) >|= (_.head)
  }

  def user_update_location (user_id : Long, new_x : Double, new_y : Double) = {
    val query_x = userDB.filter(_.id === user_id).map(_.location_x)
    val query_y = userDB.filter(_.id === user_id).map(_.location_y)
    exec (query_x.update (new_x))
    exec (query_y.update (new_y))
  }

  def user_move_up (user_id : Long) = {
    val user_x = exec (userDB.filter(_.id === user_id).map(_.location_x).result)
    val new_x = user_x >|= (_.head) >|= (_ + 1.0)
    val query_x = userDB.filter(_.id === user_id).map(_.location_x)
    new_x >>= (u => exec (query_x.update (u : Double)))
  }

  def user_move_down (user_id : Long) = {
    val user_x = exec (userDB.filter(_.id === user_id).map(_.location_x).result)
    val new_x = user_x >|= (_.head) >|= (_ - 1.0)
    val query_x = userDB.filter(_.id === user_id).map(_.location_x)
    new_x >>= (u => exec (query_x.update (u : Double)))
  }

  def user_move_right (user_id : Long) = {
    val user_y = exec (userDB.filter(_.id === user_id).map(_.location_y).result)
    val new_y = user_y >|= (_.head) >|= (_ + 1.0)
    val query_y = userDB.filter(_.id === user_id).map(_.location_y)
    new_y >>= (u => exec (query_y.update (u : Double)))
  }

  def user_move_left (user_id : Long) = {
    val user_y = exec (userDB.filter(_.id === user_id).map(_.location_y).result)
    val new_y = user_y >|= (_.head) >|= (_ - 1.0)
    val query_y = userDB.filter(_.id === user_id).map(_.location_y)
    new_y >>= (u => exec (query_y.update (u : Double)))
  }

  def user_location (user_id : Long) = {
    val user_loc = userDB.filter(_.id === user_id)
    exec (user_loc.map(u => (u.location_x, u.location_y)).result) >|= (_.head)
  }
}

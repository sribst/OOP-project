import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

object UserDB extends DB {

  final case class UserAlreadyExists (user : String) extends Exception

  lazy val userDB = TableQuery[USERDB]

  exec(userDB.schema.create)

  def users =
    exec (userDB.map(_.name).result)

  def user_exists (name : String) =
    exec (userDB.filter(_.name === name).exists.result)

  def user_add (name : String, password : String) =
    if (user_exists (name))
      throw new UserAlreadyExists (name)
    else
      exec (userDB += User (name, password))

  def is_password (name : String) (password : String) =
    exec (userDB.filter(
      u => u.name === name && u.password === password
    ).exists.result)

  def user_id (name : String, password : String) = {
    val result = exec (userDB.filter(
      u => u.name === name && u.password === password
    ).map (_.id).result)
    result.head
  }

  def user_update_password (user_id : Long) (new_password : String) = {
    val query = userDB.filter(_.id === user_id).map(_.password)
    exec (query.update (new_password))
  }
}

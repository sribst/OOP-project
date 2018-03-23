import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

// Waiting for core ?
final case class User (
  name     : String,
  password : String,
  id       : Long = 0L
)


// Schema for the USER table
final class USERDB (tag : Tag) extends Table[User] (tag, "USER") {

  def id = column[Long]("USER_ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("USER_NAME")

  def password = column[String]("USER_PWD")

  // add unique constraint on names
  def nameIndex = index("name_index", name, unique=true)

  def * = (name,password,id) <> (User.tupled, User.unapply)
}

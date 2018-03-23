import org.scalatest._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

class UserSuite extends FunSuite {

  UserDB.user_add ("Orace", "something")
  UserDB.user_add ("Laika", "something")

  test ("Users inserted") {
    assert (UserDB.users.length == 2)
  }

  test ("User exists") {
    assert (UserDB.user_exists ("Orace"))
  }


}

import org.scalatest._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future
import scala.concurrent.duration._
import db._
import ext.MyFuture._

class UserSuite extends FunSuite {

  UserDB.add_user ("Orace", "something")
  UserDB.add_user ("Laika", "something")


  test ("Users inserted") {
    UserDB.users >|= (users => assert (users.length == 2)) run
  }

  test ("User exists") {
    UserDB.user_exists ("Orace") >|= (assert (_)) run
  }

  test ("User Password Check") {
    UserDB.is_password ("Orace", "something") >|= (assert (_)) run
  }

}

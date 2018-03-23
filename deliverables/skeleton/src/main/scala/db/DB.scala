import scala.concurrent.Await
import scala.concurrent.duration._
import slick.driver.H2Driver.api._

trait DB {

  val db : Database = Database.forConfig("h2mem1")

  def exec[T](program : DBIO[T]) : T =
    Await.result (db.run(program), Duration.Inf)
}

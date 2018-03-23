package db
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import ext.MyFuture._

object ItemDB extends DB {

  lazy val itemDB = TableQuery[ITEMDB]

  exec(itemDB.schema.create)

  def items =
    exec (itemDB.map(x => x).result)

  def item (id : Long) =
    exec (itemDB.filter (i => i.id === id) result) >|= (_ head)

  def add_item (name : String, ty : Int) =
    exec (itemDB += Item (name,ty))

}

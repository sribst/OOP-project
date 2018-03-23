package db
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import ext.MyFuture._

object GenItemDB extends DB {

  lazy val genItemDB = TableQuery[GENITEMDB]

  exec(genItemDB.schema.create)

  def item (id : Long) =
    exec (genItemDB.filter (p => p.id === id) result) >|= (_ head)

  def items (x : Double, y : Double) =
    exec (genItemDB.filter (p => p.x === x && p.y === y) result)

  def add_item (baseItem : Long, x : Double, y : Double) =
    exec (genItemDB += GenItem (baseItem, x, y))

}

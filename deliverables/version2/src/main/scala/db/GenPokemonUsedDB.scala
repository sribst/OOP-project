package db
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import ext.MyFuture._
import scala.concurrent.ExecutionContext.Implicits.global

object GenPokemonUsedDB extends DB {

  lazy val usedDB = TableQuery[GENPOKEMONUSEDDB]

  exec(usedDB.schema.create)

  def used_exists (gen_id : Long, user_id : Long) = {
    exec (usedDB.filter(
      u => u.gen_id === gen_id && u.user_id === user_id
    ).exists.result)
  }

  def used_update (gen_id : Long, user_id : Long, timestamp : Long) = {
    val query = usedDB.filter(
      u => u.gen_id === gen_id && u.user_id === user_id
    ).map(_.timestamp)
    exec (query.update (timestamp))
  }

  def set_used (gen_id : Long, user_id : Long) = {
    val timestamp: Long = System.currentTimeMillis / 1000
    used_exists (gen_id, user_id) ?>= (
      used_update (gen_id, user_id, timestamp),
      exec (usedDB += GenPokemonUsed (gen_id,user_id,timestamp))
    )
  }

  def is_valid (gen_id : Long, user_id : Long, time : Long) = {
    val currenttime : Long = System.currentTimeMillis / 1000
    used_exists (gen_id, user_id) ?>= (
      exec (usedDB.filter (
	u => u.gen_id === gen_id && u.user_id === user_id
      ).result).map (_ head) >|= (u => u.timestamp < (currenttime - time)),
      Future { true }
    )
  }
}

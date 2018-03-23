package db
import scala.concurrent.Future
import slick.driver.H2Driver.api._
import ext.MyFuture._
import common.ItemType
object OwnedItemDB extends DB {

  lazy val ownedDB = TableQuery[OWNEDITEMDB]

  exec(ownedDB.schema.create)

  def has_ball (owner_id : Long) = {
    val ball = ItemType(ItemType.BALL)
    def is_ball (o : OwnedItem) = ItemDB item (o baseItemId) >|= (i => i.ty == ball)
    all_owned (owner_id) >|= (_ filter (i => i.count != 0 && (is_ball (i) run))) >|= (l => l.length > 0)
  }

  def consume_ball (owner_id : Long) = {
    val ball = ItemType(ItemType.BALL)
    def is_ball (o : OwnedItem) = ItemDB item (o baseItemId) >|= (i => i.ty == ball)
    val all = all_owned (owner_id)
    val balls = all >|= (l => l filter (o => is_ball (o) run))
    val non_empty = balls >|= (l => l filter (o => o.count > 0L) head)
    non_empty >>= (ne => decr_count (ne.baseItemId, owner_id))
  }

  def all_owned (owner_id : Long) =
    exec (ownedDB.filter (i => i.ownerId === owner_id) result)

  def owned (base_id : Long, owner_id : Long) =
    exec (ownedDB.filter (i =>
      i.ownerId === owner_id && i.baseItemId === base_id) result) >|= (_.head)

  def add_owned (base_id : Long, owner_id : Long, n : Long) = {
    owned_exists (base_id, owner_id) ?>= (
      owned_count (base_id, owner_id) >>= (count =>
	update_count (base_id, owner_id, count + n)),
      exec (ownedDB += OwnedItem (base_id,owner_id,n))
    )
  }

  def owned_exists (base_id : Long, owner_id : Long) =
    exec (ownedDB.filter(
      i => i.baseItemId === base_id && i.ownerId === owner_id
    ).exists.result)

  def owned_count(base_id : Long, owner_id : Long) =
    exec (ownedDB.filter(
      i => i.baseItemId === base_id && i.ownerId === owner_id
    ).map (_.count).result) >|= (_.head)

  def update_count (base_id : Long, owner_id : Long, new_count : Long) = {
    val query = ownedDB.filter(
      i => i.baseItemId === base_id && i.ownerId === owner_id
    ).map(_.count)
    exec (query.update (new_count))
  }

  def incr_count (base_id : Long, owner_id : Long) = {
    owned_count (base_id,owner_id) >|= (p =>
      update_count (base_id,owner_id, p + 1)
    )
  }

  def decr_count (base_id : Long, owner_id : Long) = {
    owned_count (base_id,owner_id) >|= (p =>
      update_count (base_id,owner_id, p - 1)
    )
  }

}

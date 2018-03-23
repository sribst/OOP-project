abstract class BaseItem[T] (
  val id    : Int,
  val name  : String 
) extends Collectable {

  type InteractionTarget = T

  def interact (t : T)
}

class CollectedItem (
  val id : Int,
  val name : String,
  val baseItem : BaseItem[Token],
  val owner : Int,
  var quantity : Int
) extends Collected
with Owned[Int] {

  type InteractionTarget = baseItem.InteractionTarget

  def interact (t : InteractionTarget) = baseItem.interact (t)
}

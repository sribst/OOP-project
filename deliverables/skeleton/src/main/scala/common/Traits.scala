
abstract trait Located {
  type Coordinates <: Comparable [Coordinates]
  def getCoordinates : Coordinates
}
abstract trait MutableLocated extends Located {
  def setCoordinates (c : Coordinates)
}

abstract trait ComparisonOperators[T] {
  def < (t : T) : Boolean
  def > (t : T) : Boolean
}

abstract trait ArithOperators[T] {
  def + (t : T) : T
  def - (t : T) : T
}

abstract trait Named {
  val name : String
}

abstract trait Quantified {
  var quantity : Int
}

abstract trait TimeStamped {
  type TimeStamp <: Comparable [TimeStamp]
  def getTimeStamp : TimeStamp
  def setTimeStamp (ts : TimeStamp)
}

abstract trait Interactive {
  type InteractionTarget
  def interact (t : InteractionTarget)
}

trait Identified[T] {
  val id : T
}

trait Owned[T] {
  val owner : T
}

/* ============================================================================
 * ================================Monadic Type================================
 * ============================================================================
 */

trait Monadic[+T] {
  type M[T] <: Monadic[T]
  def >>=[S] (f : T => M[S]) : M[S]
}
trait StaticMon {
  type M[T] <: Monadic[T]
  def ret[T] (t : T) : M[T]
  def run[T] (mon : M[T]) : T
  def apply[T] (t : T) = ret (t)
}


/* ============================================================================
 * ========================Monadic friendly Option Type========================
 * ============================================================================
 */

sealed abstract class Opt[+T] extends Monadic[T]{
  type M[T] = Opt[T]
  def >>=[S] (f : T => M[S]) : M[S] = this match {
    case MoNone => MoNone
    case MoSome (t) => f (t)
  }
}
case object MoNone extends Opt[Nothing]
case class MoSome[T] (i : T) extends Opt[T]
object Opt {
  object MoNoneValue extends Throwable
  object Monad extends StaticMon {
    type M[T] = Opt[T]
    def ret[T] (i : T) = MoSome (i)
    def run[T] (m : M[T]) = m match {
      case MoSome (i) => i
      case MoNone => throw MoNoneValue
    }
  }
}

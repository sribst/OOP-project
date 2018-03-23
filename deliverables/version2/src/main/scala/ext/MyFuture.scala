package ext

import scala.concurrent.{ Future, Await }
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Success, Failure }
import akka.pattern.after
import akka.actor.{ ActorSystem }

class MyFuture[S] (val future : Future[S]) {

  def >>=[T] (f : S => Future[T]) = future flatMap f

  def >|=[T] (f : S => T) = future map f

  def ?>=[T] (
    thenF : =>Future[T],
    elseF : =>Future[T]
  ) = this >>= ((r : S) => if (r == true) thenF else elseF)

  def ?|=[T] (
    thenF : =>T,
    elseF : =>T
  ) = this >|= ((r : S) => if (r == true) thenF else elseF)

  def run (d : Int) : S = Await.result (future, d seconds)
  def run : S = run (1)

  def onComp[T] (onSuccess : S => T, onFailure : Throwable => T) =
    future onComplete {
      case Success (succ) => onSuccess (succ)
      case Failure (fail) =>  onFailure (fail)
    }

  def toFutureTry = future map (Success (_)) recover {case x => Failure(x)}

}
object MyFuture {
  implicit def coerce[T] (f : Future[T]) = new MyFuture (f)
  implicit def coerce[T] (mf : MyFuture[T]) = mf future
  def liftSeq[T] (lf : Seq[Future[T]]) : Future[Seq[T]] = {
    val flt = Future.sequence (lf map (_ toFutureTry))
    flt map (_ filter (_ isSuccess) map (_ get))
  }
}


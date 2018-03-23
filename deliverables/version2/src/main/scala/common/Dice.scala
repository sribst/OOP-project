package common
import scala.util.Random

object Dice{
  val dice = new Random(System.currentTimeMillis)
  def roll(low:Int, high:Int) : Int = {
    (dice nextInt (high - low)) + low
  }

  def rollLong(low:Long, high:Long) : Long = {
    ((roll (low toInt, high toInt)) toLong) + low
    // (dice nextLong (high - low)) + low
  }

}

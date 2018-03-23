package common.coordinates

class Coordinates (val x : Double, val y : Double)

object Coordinates {
  def apply (x : Double, y : Double) = new Coordinates (x, y)
  implicit def tupleToCoordinates (t : (Double, Double)) =
    new Coordinates (t._1, t._2)
}

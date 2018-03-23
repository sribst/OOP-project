
class PointOfInterest(
  private val coords : D2Coordinates,
  val name : String,
  val id : Int
)
extends Location
with D2Located
with PlayerInteractive
with Identified[Int] {

  def interact (p : Player) = ()

  def getCoordinates = coords

}

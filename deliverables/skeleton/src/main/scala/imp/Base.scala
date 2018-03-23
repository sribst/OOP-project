
class D2Coordinates (
  var x : Int,
  var y : Int
)
extends Comparable[D2Coordinates]
with ComparisonOperators[D2Coordinates]
with ArithOperators[D2Coordinates]
 {

  def compareTo (c : D2Coordinates) =
    if (x == c.x && y == c.y) 0
    else if (x < c.x && y < c.y) -1
    else if (x > c.x && y > c.y) 1
    else -2

  def + (c : D2Coordinates) =
    new D2Coordinates (x + c.x, y + c.y)

  def - (c : D2Coordinates) =
    new D2Coordinates (x - c.x, y - c.y)

  def < (c : D2Coordinates) =
    (this compareTo c) == -1

  def > (c : D2Coordinates) =
    (this compareTo c) == 1
}


trait D2Located extends Located {
  type Coordinates = D2Coordinates
}

trait MutableD2Located extends MutableLocated with D2Located

trait PlayerInteractive extends Interactive {
  type InteractionTarget = Player
}


class TRenderable (
  val what : Char,
  var where : D2Coordinates
) extends Renderable {

  type RenderingData = (Char, D2Coordinates)

  def renderData = (what, where)

}

class LayerRenderable (
  val what : String
) extends Renderable {
  
  type RenderingData = String
  
  def renderData = what
  
}

trait RenderingLayer[T<:Renderable] extends Rendering {
  type I = T
  type O = LayerRenderable

  protected var registered = List[I] ()
  def register (r : I) = registered = r :: registered

}

object MapLayer extends RenderingLayer[TRenderable] {

  private var viewpoint = new D2Coordinates (0, 0)
  def setViewPoint (c : D2Coordinates) = viewpoint = c

  private val view_width  = 11
  private val view_height = 11
  private val view_coor = new D2Coordinates (view_width / 2, view_height / 2)

  implicit private def tup2Coor (t : Tuple2[Int,Int]) =
    new D2Coordinates (t._1, t._2)

  private def isInView (t : TRenderable) = {
    val tc = this toRelative t.where
    (tc > (-1,-1) && tc < (view_width, view_height))
  }

  private def toRelative (c : D2Coordinates) : D2Coordinates =
    c - viewpoint +  view_coor

  def render = {

    def empty (x : Int, y : Int) = {
      var e = new Array[Array[Char]] (y)
      for (i <- 0 to (y - 1)) {
	e(i) = new Array[Char] (x)
	for (j <- 0 to (x - 1)) e(i)(j) = ' '
      }
      e
    }

    var res = empty(view_width, view_height)
    val viewable = registered filter isInView
    viewable.foreach {r =>
      val (what,c) = r.renderData
      val c_rel = this toRelative c
      val (x,y) = (c_rel.x, c_rel.y)
      res(view_height - 1 - y)(x) = what
    }
    new O (res map (_ mkString "") mkString "\n")
  
  }

}

class RenderableMessage(val what : String) extends Renderable {
  type RenderingData = String
  def renderData = what
}

object MessageLayer extends RenderingLayer[RenderableMessage] {
  def render = new O (registered map (_ renderData) mkString "\n")
}

object TerminalRenderer extends Rendering {

  type I = LayerRenderable
  type O = String

  private var registered = List[I] ()

  def register (r : I) = registered = r :: registered

  def render = (registered.map (_ renderData) mkString "\n") + "\n"

}

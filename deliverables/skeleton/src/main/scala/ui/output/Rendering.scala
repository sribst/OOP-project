/* The abstract trait Renderable describes the basic structure required from an
 * object to be rendered.
 */
abstract trait Renderable {

  type RenderingData

  def renderData : RenderingData

}

abstract trait Rendering {
  /* The type of objects we want to render */
  type I <: Renderable
  /* The type of the output for the render function */
  type O
  
  def register (i : I)
  
  def render : O

}


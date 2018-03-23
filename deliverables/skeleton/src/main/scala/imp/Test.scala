object Test extends App {
  override def main (args : Array[String]) {
    val a = new TRenderable('A',new D2Coordinates(0,0))
    val b = new TRenderable('B',new D2Coordinates(0,1))
    val c = new TRenderable('C',new D2Coordinates(1,0))
    val m = new RenderableMessage("salut les copains!")
    MessageLayer.register(m)
    MapLayer.register (a)
    MapLayer.register (b)
    MapLayer.register (c)
    TerminalRenderer.register (MessageLayer.render)
    TerminalRenderer.register (MapLayer.render)
    print(TerminalRenderer.render)
    TInput.readInputs
  }
}

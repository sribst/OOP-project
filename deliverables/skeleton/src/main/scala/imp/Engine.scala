/*
class PokemonGOGameState (
  val player : Player,
  private var position : D2Coordinates,
  var locations : List[PointOfInterest]
) extends GameState with MutableD2Located {

  type RenderingData = (LayerRenderable, LayerRenderable)
  type player = Player
  type location = PointOfInterest

  def renderData = (new LayerRenderable (""), new LayerRenderable (""))
  def getCoordinates = position
  def setCoordinates (c : D2Coordinates) = position = c

}

object PokemonGOGameLogic extends GameLogic {
  type action = List[TInput.Action]
  def apply (s : GameState, i : action) : GameState = s
}

object Renderer extends Rendering {
  type I = PokemonGOGameState
  type O = Unit
  private var state = (None : Option[I])
  def register (s : I) = state = Some (s)
  def render = {
    state flatMap (s => Some (TerminalRenderer register (s.renderData._1)))
    state flatMap (s => Some (TerminalRenderer register (s.renderData._2)))
    TerminalRenderer.render
  }
}

class PokemonGOGameEngine extends GameEngine {

  type inputEngine = TInput.type
  type renderingEngine = Renderer.type
  type gameState = PokemonGOGameState
  type gameLogic = PokemonGOGameLogic.type

  val input = TInput
  val renderer = Renderer
  var state : gameState
  val gameRules : gameLogic

  def start = ()

  def update = {
    input.readInputs
    val i = input getInputs 1
    state = gameRules (state, i)
    renderer register state
    renderer.render
  }
}

*/

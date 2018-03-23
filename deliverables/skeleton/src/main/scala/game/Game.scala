
trait GameState extends Renderable with MutableLocated {

  type player <: IPlayer
  type location <: Location

  def player : player
  def locations : Seq[location]
}

trait GameLogic {
  type action
  def apply (s : GameState, i : action) : GameState
}

trait GameEngine {

  type inputEngine     <: InputEngine
  type renderingEngine <: Rendering
  type gameState       <: GameState
  type gameLogic       <: GameLogic

  val input : inputEngine
  val renderer : renderingEngine
  var state : gameState
  val gameRules : GameLogic

  def start

  def update

}

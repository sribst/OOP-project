
/* A basic implementation for the InputEngine using the terminal
 *
 * NB:
 * 
 *   -It is still missing some features like more actions.
 *
 *   -Maybe we should have a separated definition of the actions as it might
 *    make sense for them to be in the general abstract architecture instead
 *    of being in the implementation. Thoughts?
 *
 */


/* First we need a parser to read an input string from the shell and translate
 * it to an object of type Action that we will be able to use.
 */

/* Basics for understanding the implementation of the parser
 * 
 * 1) Creating a regex object :
 *
 *   val r = """regex""".r
 *           or
 *   val r = new RegEx("regex")
 *
 * 2) We pass the string matched by the regex to the provided function to build
 *    our resulting object:
 * 
 *   def produce = r ^^ { arg => .... }
 *
 *       which is equivalent to
 * 
 *   [Grammar Rule] -> [Production Rule]
 *
 *
 * 
 * We can also combine multiple production/parsing rules:
 *
 *   def produce2 = produce ~ produce ^^ { arg1 ~ arg2 => .... }
 *
 * 
 * And probably do a lot of other amazing stuff :-).
 */
import scala.util.parsing.combinator._
trait InputParser extends RegexParsers {

  /* We will use case classes for our Actions and their attributes when relevant
   * this will ease our future work by allowing us to use pattern matching.
   * (even if the syntax feels HEAVY !!!)
   */
  sealed abstract class Direction
  case object Up    extends Direction
  case object Down  extends Direction
  case object Left  extends Direction
  case object Right extends Direction

  sealed abstract class Action
  case class Move (d : Direction) extends Action


  /* We define the regular expressions we will need to parse the entry and
   * their associated production rules when we can already do it.
   */
  val move  = """move""".r
  def up    = """up""".r    ^^ { _ => Up    }
  def down  = """down""".r  ^^ { _ => Down  }
  def left  = """left""".r  ^^ { _ => Left  }
  def right = """right""".r ^^ { _ => Right }

  /* Parsing rule for actions
   */
  def action : Parser[Action] =
    move ~ direction ^^ { case _ ~ d => Move (d) }
  
  /* Parsing rule for directions
   */
  def direction : Parser[Direction] =
    (up | down | left | right) ^^ { d => d }

}

/* As we just defined the InputParser, it will provide us with a
 * [parse] function, now we need to have a concrete implementation of our
 * abstract trait InputEngine.
 */
abstract trait TerminalInputEngine extends InputEngine with InputParser {
  import scala.io._
  import scala.collection.mutable.ListBuffer

  /* As we are aiming for a concrete implementation we need to provide the
   * abstract fields we are missing.
   */
  var inputs = ListBuffer[Action] ()

  /* Pretty simple:
   *
   *  -Read from the standard input.
   *
   *  -Give the result to the parse function we inherited from InputParser with
   *   the action parsing function.
   *
   *  -Match over the return of the parsing and add it to the inputs buffer
   *   if it succeded, display an error message when it fails.
   *
   * NB:
   * I didn't look at the difference between a Failure and an Error from
   * the parsing function (to be determined)
   */
  def readInputs = {
    val s = StdIn.readLine
    val r = parse (action, s) match {
      case Success(m,_) =>
	inputs += m
      case Failure (msg,_) =>
	println ("FAILURE: " + msg)
      case Error (msg,_) =>
	println ("ERROR: " + msg)
    }
  } 
}
/* Tadaaaaa! */

object TInput extends TerminalInputEngine

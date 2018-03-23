import scala.collection.mutable.ListBuffer

/* The InputEngine provides an abstraction of a component aimed at :
 * 
 * -Gathering inputs and translating them to a more convenient type [Action].
 * -Providing a function to retrieve the actions so other components can use it.
 *
 * NB: At some point we might need to have a more concurrent friendly
 * definition as it would make sense to have the input gathering process
 * run separately from the rest to avoid missing real time inputs.
 */
abstract trait InputEngine {
  
  type Action
  
  var inputs : ListBuffer[Action]
  
  def readInputs
  
  def getInputs (n : Int) : List[Action] = {
    val l = inputs drop n
    inputs remove (0,n)
    return (l toList)
  }

}


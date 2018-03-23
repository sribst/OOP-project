abstract class UserInterface {
   def print(message : String) : Unit
   
   def userInfos : String //Was (String,String)
   
   def initGame : Unit
   
   def askForAction : Unit
}

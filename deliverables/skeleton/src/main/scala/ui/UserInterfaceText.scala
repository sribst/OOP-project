import scala.io._

// il faudrait recupere l'id du personnage
class UserInterfaceText extends UserInterface {
  
  def print(message : String) = println(message)
  
  def userInfos : String = { //Was (String, String)
     println("Username : ")
     val username = scala.io.StdIn.readLine
     println("Password : ")
     val password = scala.io.StdIn.readLine
     return username+":"+password
  }
  
  def initGame {
    var answer = ""
    while (!answer.equals("0") && !answer.equals("1")) {
      println("Welcome!")
      println("Type 0 to sign in or 1 to create an account.")
      answer = scala.io.StdIn.readLine
    }
	val info= userInfos
    answer match {
      case "0" => 
      val infos = "SIGN"+ info
	//reste a l'envoye au serveur et attendre la reponse	

      case "1" =>
	 val infos = "CACC"+ info

        //Reste a l'envoyer au serveur et attendre la reception
	

        //println("Account created!")
	// on effectura cette ligne si on recoit le message CAOK
        initGame
    }
  }
  
  def askForAction {
	var answer= ""
	println("1) look your item \n2)look your Pokemon \n3)move \n4)Interaction")
	println("What do you want to do?")
	answer = scala.io.StdIn.readLine
	answer match{
		case "1" => 
 			println("send message \n")    			
     		case "2" =>
			println("send message \n") 
		case "3" =>
			println("where do you want to go ? or press 1 to open menu\n")
 
		case "4" =>	
			println("send message \n") 	

	}	 

   
  }
}

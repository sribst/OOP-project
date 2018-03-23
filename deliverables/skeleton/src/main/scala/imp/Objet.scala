abstract class ObjetPoke{
  val id   : Int
  val name : String
}

abstract class ObjetSoin extends ObjetPoke{
      	 def utiliser(x:Pokemon)
}

abstract class ObjetSoinStatut extends ObjetSoin{
	 val statut:String
}

abstract class ObjetSoinPV extends ObjetSoin{
	 val soin:Int
}


abstract class ObjetCapture extends ObjetPoke{
	 val pourcentage_capture:Double
	 def envoi(x:Pokemon)
	 
}

abstract class ObjetTechnique extends ObjetPoke

abstract class ObjetDivers extends ObjetPoke


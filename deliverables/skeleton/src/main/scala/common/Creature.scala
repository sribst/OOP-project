
abstract trait Creature extends Token{
	 var pv_cur:Int
	 var pv_max:Int
	 def soin()
	 
}

trait CapturedCreature extends Creature with Owned[Int]






abstract trait IUser extends Named with Identified[Int]

abstract trait IPlayer extends Named with Identified[Int] with Owned[Int] {
  def creatures : List[CapturedCreature]
  def items : List[CollectedItem]
}

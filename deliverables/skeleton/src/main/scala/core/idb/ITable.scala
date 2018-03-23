
abstract trait ITableGet [Arg, Obj] {
  def get (arg : Arg) : Obj
}
abstract trait ITableAdd [Obj] {
  def add (obj : Obj) : Int
}
abstract trait ITableUpdate [Obj] {
  def update (obj : Obj)
}
abstract trait ITableRemove [Obj] {
  def remove (obj : Obj)
}

abstract trait ITable [Arg, Obj <: Identified[Int]]
extends ITableGet [Arg, List[Obj]]
with ITableAdd [Obj]
with ITableUpdate [Obj]
with ITableRemove [Obj]

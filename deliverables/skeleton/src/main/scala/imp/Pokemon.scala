abstract class Pokemon (
  val id   : Int,
  val name : String
)
extends Creature

abstract class CapturedPokemon(
  val id    : Int,
  val owner : Int,
  val name  : String,
  val basePokemon : Pokemon
) extends CapturedCreature

// partie qui gere les types pour les pokemons et les attaques

trait Type{
     // var typelist:List[String]=List()
     // var typefaiblesse:List[String]=List()
     // var typefort:List[String]=List()
}

trait Normal extends Type{
     // typelist="Normal":: typelist
}

trait Feu extends Type{
     // typelist="Feu":: typelist
}

trait Eau extends Type{
   //   typelist="Eau":: typelist
}

trait Plante extends Type {

}
trait Electrique extends Type
trait Glace extends Type
trait Combat extends Type
trait Poison extends Type
trait Sol extends Type
trait Vol extends Type
trait Psy extends Type
trait Insecte extends Type
trait Roche extends Type
trait Spectre extends Type
trait Dragon extends Type
trait Tenebre extends Type
trait Acier extends Type
trait Fee extends Type



abstract class Attaque extends Type{
	 val nom:String
	 val degat:Integer
	 val descritpion: String
	 val precision:Integer
	 //def calcul_degat(x:Pokemon)
}

trait stat extends Creature{
      var Attaque:Integer
      var Defense:Integer
      var Attaque_special:Integer
      var Defense_special:Integer
      var Vitesse:Integer
}


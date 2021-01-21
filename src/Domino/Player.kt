package Domino

class Player(val name:String) {
  val handNabor= mutableListOf<Domino>()

  override fun toString(): String {
    return "Player(name='$name', handNabor=$handNabor)"
  }

  fun nextStep(game: Game) {
    TODO("Not yet implemented")
  }

}
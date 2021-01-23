package domino
class Player(val name:String) {
  val hand = mutableListOf<Domino>()

  override fun toString(): String {
    return "Name: $name,\thand=$hand\n"
  }

  fun handIsNotFull(maxHandSize: Int): Boolean{
    return hand.size < maxHandSize
  }

}
package Domino

class GameStep(var domino: Domino, var player: Player, var step: Int) {
    override fun toString(): String {
        return "$domino (${player.name}, крок №$step)"
    }

    fun domino():Domino{
        return domino
    }
}
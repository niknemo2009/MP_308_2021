package domino

import java.lang.NumberFormatException

class Player(val name: String) {
    val hand = mutableListOf<Domino>()

    override fun toString(): String {
        return "Name: $name,\thand=$hand\n"
    }

    fun handIsNotFull(maxHandSize: Int): Boolean {
        return hand.size < maxHandSize
    }

    fun makeMove(game: Game) {
        val ableDominoesMap = game.getAbleDominoesIndexes(hand)
        var ableDominoes = mutableListOf<Domino>()
        ableDominoesMap.forEach {
            it.value.forEach { idx ->
                ableDominoes.add(hand[idx])
            }
        }

        ableDominoes = ableDominoes.distinct().toMutableList()
        println(ableDominoesMap)
        println(ableDominoes)
        if (ableDominoes.size > 1) {
            selectFromList(ableDominoes)

        }
    }

    fun selectDominoFromHand(): Domino? {
        if (hand.isEmpty()) {
            println("Your hand is empty")
        } else {
            println("Your hand:\t $hand")
            println("Input number of domino you want to choose")
            return hand[getUserNumber(hand.size)]
        }
        return null
    }

    private fun selectFromList(dominoes: List<Domino>): Domino {
        println("Able dominoes:\t${dominoes}")
        println("Input number of domino you want to choose")
        return dominoes[getUserNumber(dominoes.size)]
    }

    private fun getUserNumber(maxNumber: Int): Int {
        var inputtedNum: Int
        while (true) {
            try {
                inputtedNum = Integer.valueOf(readLine()) - 1
            } catch (ex: NumberFormatException) {
                println("Input a number, please!")
                continue
            }

            if (inputtedNum in 0 until maxNumber) {
                break
            } else {
                println("Number must be greater than 0 and less than $maxNumber")
            }
        }
        return inputtedNum
    }
}
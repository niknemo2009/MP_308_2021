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
        println("ableDominoesMap:\t$ableDominoesMap")
        //println("ableDominoes:\t$ableDominoes")
        //println("hand:\t$hand")
        val selectedDomino = when {
            ableDominoes.size > 1 -> {
                selectFromList(ableDominoes)
            }
            ableDominoes.size == 1 -> {
                ableDominoes[0]
            }
            else -> return
        }

        val idx = hand.indexOf(selectedDomino)
        val canBeAddedLeft = ableDominoesMap["left"]?.contains(idx) == true
        val canBeAddedRight = ableDominoesMap["right"]?.contains(idx) == true

        addLeftOrRight(game, selectedDomino, canBeAddedLeft, canBeAddedRight)

    }
    private fun addLeftOrRight(game: Game, domino: Domino, canBeAddedLeft: Boolean, canBeAddedRight: Boolean){
        println("CBAL:\t$canBeAddedLeft\nCBAR:\t$canBeAddedRight")
        println("Domino:\t$domino")
        if (canBeAddedLeft && canBeAddedRight) {
            println("Choose where you want to place the domino:")
            println("\t1:\tleft\n" +
                    "\t2:\tright")
            if (getUserNumber(2) == 1) {
                game.addDominoToTableLeft(domino)
            } else {
                game.addDominoToTableRight(domino)
            }
        } else if (canBeAddedLeft) {
            println("added left")
            game.addDominoToTableLeft(domino)
        } else {
            println("added right")
            game.addDominoToTableRight(domino)
        }
    }
    fun selectDominoFromHand(): Domino? {
        if (hand.isEmpty()) {
            println("Your hand is empty")
        } else {
            println("Your hand:\t $hand")
            println("Input number of domino you want to choose")
            return hand[getUserNumber(hand.size) - 1]
        }
        return null
    }

    private fun selectFromList(dominoes: List<Domino>): Domino {
        println("Able dominoes:\t${dominoes}")
        println("Input number of domino you want to choose")
        return dominoes[getUserNumber(dominoes.size) - 1]
    }

    private fun getUserNumber(maxNumber: Int): Int {
        var inputtedNum: Int
        while (true) {
            try {
                inputtedNum = Integer.valueOf(readLine())
            } catch (ex: NumberFormatException) {
                println("Input a number, please!")
                continue
            }

            if (inputtedNum in 0 until maxNumber + 1) {
                break
            } else {
                println("Number must be greater than 0 and less than $maxNumber")
            }
        }
        return inputtedNum
    }
}
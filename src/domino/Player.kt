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

    fun makeMove(game: Game): Boolean {
        //TODO add selection from the bazaar
        val ableDominoesMap = game.getAbleDominoesIndexes(hand)
        var ableDominoes = mutableListOf<Domino>()
        ableDominoesMap.forEach {
            it.value.forEach { idx ->
                ableDominoes.add(hand[idx])
            }
        }

        ableDominoes = ableDominoes.distinct().toMutableList()
        val selectedDomino = when {
            ableDominoes.size > 1 -> {
                selectFromList(ableDominoes)
            }
            ableDominoes.size == 1 -> {
                println("You can add only one domino to the table:\t${ableDominoes[0]}")
                ableDominoes[0]
            }
            else -> {
                //TODO move this to the rules
                println("You can't add any domino to the table")
                val dominoFromBazaar = game.getDominoFromBazaar()
                if (dominoFromBazaar != null){
                    println("Selection from the bazaar")
                    println("You got a domino $dominoFromBazaar")
                    hand.add(dominoFromBazaar)
                }
                return false
            }
        }

        val idx = hand.indexOf(selectedDomino)
        val canBeAddedLeft = ableDominoesMap["left"]?.contains(idx) == true
        val canBeAddedRight = ableDominoesMap["right"]?.contains(idx) == true

        addLeftOrRight(game, selectedDomino, canBeAddedLeft, canBeAddedRight)
        hand.remove(selectedDomino)
        return true
    }

    private fun addLeftOrRight(game: Game, domino: Domino, canBeAddedLeft: Boolean, canBeAddedRight: Boolean) {
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
            game.addDominoToTableLeft(domino)
            println("Domino added left")
        } else {
            game.addDominoToTableRight(domino)
            println("Domino added right")
        }
    }

    fun selectDominoFromHand(): Domino? {
        return if (hand.isEmpty()) {
            println("Your hand is empty")
            null
        } else {
            println("Your hand:\t $hand")
            println("Input number of domino you want to choose")
            hand[getUserNumber(hand.size) - 1]
        }
    }

    private fun selectFromList(dominoes: List<Domino>): Domino {
        println("%-18s-\t${dominoes}".format("Able dominoes"))
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
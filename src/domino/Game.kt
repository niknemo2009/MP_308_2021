package domino

import java.nio.charset.CharacterCodingException
import kotlin.reflect.typeOf

class Game(private val players: List<Player>) {
    private val bazaar = mutableListOf<Domino>()
    private var dominoesOnTable: ArrayDeque<Domino>
    private var maxHandSize: Int = 0
    private var playersScores: MutableMap<String, Int> = mutableMapOf()
    private val currentPlayer: Player? = null

    init {
        players.map {
            playersScores.put(it.name, 0)
        }
        initDominoes()
        dominoesOnTable = ArrayDeque(bazaar.size)
        initPlayersHandsAutomatically()
    }

    fun start() {
        firstStep()

        var missedMoves = 0
        while (missedMoves != players.size) {
            missedMoves = 0
            for (player in players) {
                print("\n\n")
                println("%-18s-\t$dominoesOnTable".format("Dominoes on table"))
                println("%-18s-\t${player.name}".format("Current Player"))
                println("%-18s-\t${player.hand}".format("Your hand"))
                if (!player.makeMove(this)) {
                    if (player.hand.size == 0){
                        finishRound(player)
                    }
                    missedMoves += 1
                }
            }
        }
    }

    private fun getFirstPlayer(): Player? {
        var handsDominoes = mutableListOf<Domino>()
        var doubles = mutableListOf<Domino>()

        for (player in players) {
            player.hand.forEach {
                handsDominoes.add(it)
            }
        }
        handsDominoes.filterTo(doubles) {
            it.isDouble()
        }
        handsDominoes = handsDominoes.filterNot { it.isDouble() }.toMutableList()

        // remove [0,0] domino
        doubles = doubles.filter { it.getValue() != 0 }.toMutableList()

        return if (doubles.isNotEmpty()) {
            if (doubles.any { it.getValue() == 2 }) {
                getPlayerByDomino(doubles.first { it.getValue() == 2 })
            } else {
                val maxDoubleValue: Int = doubles.maxOf { it.getValue() }
                getPlayerByDomino(doubles.first { it.getValue() == maxDoubleValue })
            }
        } else {
            val minDominoValue = handsDominoes.minOf { it.getValue() }
            getPlayerByDomino(handsDominoes.first { it.getValue() == minDominoValue })
        }
    }

    private fun firstStep() {
        val firstPlayer = getFirstPlayer()
        println("Hello, ${firstPlayer?.name}, you go first")
        // at this stage domino cannot be null
        // but i don't wont to break the logic of the function
        val domino = firstPlayer?.selectDominoFromHand()
        if (domino != null) {
            dominoesOnTable.addFirst(domino)
            firstPlayer.hand.remove(domino)
        }
    }

    private fun getPlayerByDomino(domino: Domino): Player? {
        for (player in players) {
            if (domino in player.hand)
                return player
        }
        return null
    }

    private fun initPlayersHands() {
        // TODO test this function
        for (player in players) {
            println("Hello, ${player.name}. Input the domino number you want to take.")
            var selectedDominoNumber: Int
            while (player.handIsNotFull(maxHandSize)) {
                println("Current max number is:\t${bazaar.size}")
                try {
                    selectedDominoNumber = Integer.valueOf(readLine())
                    player.hand.add(bazaar[selectedDominoNumber + 1])
                    bazaar.removeAt(selectedDominoNumber)
                } catch (ex: NumberFormatException) {
                    println("You must enter a number, not anything else!")
                } catch (ex: IndexOutOfBoundsException) {
                    println("You must enter a number, that less than max number")
                }
            }
        }
    }

    private fun initPlayersHandsAutomatically() {
        for (player in players) {
            var i = 0
            while (i < maxHandSize) {
                player.hand.add(bazaar[0])
                bazaar.removeAt(0)
                i++
            }
        }
    }

    private fun initDominoes() {
        maxHandSize = if (players.size > 2) {
            5
        } else {
            7
        }
        for (i in 0..6) {
            for (j in i..6) {
                bazaar.add(Domino(i, j))
            }
        }
        bazaar.shuffle()
    }

    fun getAbleDominoesIndexes(dominoes: List<Domino>): Map<String, ArrayList<Int>> {
        val leftDominoValue = dominoesOnTable.first().leftValue
        val rightDominoValue = dominoesOnTable.last().rightValue
        val resultMap = mutableMapOf(
                "left" to arrayListOf<Int>(),
                "right" to arrayListOf())

        dominoes.forEachIndexed { i, domino ->
            if (domino.leftValue == leftDominoValue || domino.rightValue == leftDominoValue) {
                resultMap["left"]?.add(i)
            }
            if (domino.leftValue == rightDominoValue || domino.rightValue == rightDominoValue) {
                resultMap["right"]?.add(i)
            }
        }
        return resultMap
    }

    fun getDominoFromBazaar(): Domino? {
        // bazaar must be shuffled
        if (bazaar.size != 0) {
            return bazaar.removeAt(0)
        }
        println("Bazaar is empty")
        return null
    }

    fun addDominoToTableLeft(domino: Domino) {
        if (domino.rightValue != dominoesOnTable.first().leftValue)
            domino.rotate180()
        dominoesOnTable.addFirst(domino)
    }

    fun addDominoToTableRight(domino: Domino) {
        if (domino.leftValue != dominoesOnTable.last().rightValue)
            domino.rotate180()
        dominoesOnTable.addLast(domino)
    }

    fun finishRound(winner: Player){
        for (player in players){

        }
    }
}
fun main() {
    val game = Game(listOf(
            Player("VladIsLove"),
            Player("Artemius"),
            Player("Maxxxtentacion"),
            Player("Antonio")
    ))
    game.start()
}
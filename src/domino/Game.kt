package domino

class Game(val players: List<Player>) {
    private val bazaar = mutableListOf<Domino>()
    private var maxHandSize: Int = 0

    private var currentPlayer: Player? = null
    fun start() {
        initDominoes()
        initPlayersHandsAutomatically()
        for (player in players) {
            println(player)
        }
        initFirstPlayer()

    }

    private fun initFirstPlayer() {
        var handsDominoes = mutableListOf<Domino>()
        var doubles = mutableListOf<Domino>()

        for (player in players) {
            player.hand.forEach {
                handsDominoes.add(it)
            }
        }
        // divide to doubles and not
        handsDominoes.filterTo(doubles) {
            it.isDouble()
        }
        handsDominoes = handsDominoes.filterNot { it.isDouble() }.toMutableList()

        // remove [0,0] domino
        doubles = doubles.filter { it.getValue() != 0 }.toMutableList()

        if (doubles.isNotEmpty()) {
            if (doubles.any { it.getValue() == 2 }) {
                currentPlayer = getPlayerByDomino(doubles.first())
                // TODO remove println
                println("double 1-1")
            } else {
                val maxDoubleValue: Int = doubles.maxOf { it.getValue() }
                currentPlayer = getPlayerByDomino(doubles.first { it.getValue() == maxDoubleValue })
                // TODO remove println
                println("double $maxDoubleValue")
            }
        } else {
            val minDominoValue = handsDominoes.minOf { it.getValue() }
            currentPlayer = getPlayerByDomino(handsDominoes.first { it.getValue() == minDominoValue })
            println(handsDominoes)
        }
        // TODO remove println
        println("currentPlayer:\t$currentPlayer")
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

package Domino

import java.util.*

fun main() {
    var player1 = Player("Іван")
    var player2 = Player("Микола")
    var player3 = Player("Петро")
    var player4 = Player("Аліна")

    var players = mutableListOf<Player>(player1, player2, player3, player4)
    var game = Game(players)

    game.start()
}

class Game(val players:List<Player>) {
    val reserve = mutableListOf<Domino>()// фішки
    var table = ArrayDeque<GameStep>()// хто грав якими фішками та у якому порядку
    var currentPlayer = -1// поточний гравець
    var steps = 1// номер поточного кроку у грі

    fun start(){
        // Генерація всіх фішок
        initReserve()

        // Роздати гравцям випадкові фішки
        initPlayer()

        print("Гравці та їх фішки на початку гри:\n")
        for(player in players){
            print("\t$player\n")
        }

        // Обрати першого гравця, першу фішку та здійснити перший крок за правилами гри
        firstStep()

        // Почати гру
        play()

        print("\nХід гри(розкладені фішки, гравець(який додав фішку) і крок(на якому додали фішку):\n")
        for(domino in table){
            print("\t$domino\n")
        }

        print("\nГравці та їх фішки в кінці гри:\n")
        findWinner()
    }

    private fun initReserve() {
        for (i in 0..6){
            for (j in i..6){
                var domino=Domino(i,j)
                reserve.add(domino)
            }
        }
        reserve.shuffle()
    }

    private fun initPlayer() {
        for (player in players){
            for (i in 0..4){
                getDomino(player)
            }
        }
    }

    private fun getDomino(player: Player):Boolean{
        if(reserve.isNotEmpty()){
            player.dominos.add(reserve.get(0))
            reserve.removeAt(0)

            return true
        }
        return false
    }

    private fun firstStep() {
        // Перший гравець
        var firstPlayer:Player

        // Перша фішка
        var firstDomino:Domino

        // У кого з гравців є дублі(6:6, 5:5 і т.д.)
        val doublesPlayers = doublesPlayers()

        // У кого з гравців є максимальний дубль
        if(doublesPlayers.isNotEmpty()){
            firstPlayer = firstByDoubles(doublesPlayers())
            firstDomino = firstPlayer.maxDouble()
        }else{
            // Якщо немає дублів, то має робити перший крок гравець з найбільшим значенням фішки
            firstPlayer = firstByMaxDomino()
            firstDomino = firstPlayer.maxDomino()
        }

        // Хто робить перший крок
        currentPlayer = players.indexOf(firstPlayer)

        // Перший крок
        nextStep(firstPlayer, firstDomino, true)
    }

    private fun doublesPlayers():MutableList<Player>{
        val doublesPlayers = mutableListOf<Player>()

        for(player in players) {
            if (player.hasDoubles()) {
                doublesPlayers.add(player)
            }
        }

        return doublesPlayers
    }

    private fun firstByDoubles(doublesPlayers:MutableList<Player>):Player{
        var firstPlayerByDouble = doublesPlayers[0]

        for(player in doublesPlayers){
            if(player.maxDouble().totalValue() > firstPlayerByDouble.maxDouble().totalValue()){
                firstPlayerByDouble = player
            }
        }

        return firstPlayerByDouble
    }

    private fun firstByMaxDomino():Player{
        var firstPlayer = players[0]

        for(player in players) {
            if (player.maxDomino().totalValue() > firstPlayer.maxDomino().totalValue()) {
                firstPlayer = player
            }
        }

        return firstPlayer
    }

    private fun nextStep(player: Player, domino: Domino, toRight:Boolean){
        // Забрати ту фішку, якою ходить гравець
        player.dominos.remove(domino)

        if(toRight){
            // "Покласти" забрану фішку на "стіл" З ПРАВОЇ СТОРОНИ
            table.addLast(GameStep(domino, player, steps))
        }else{
            // "Покласти" забрану фішку на "стіл" З ЛІВОЇ СТОРОНИ
            table.addFirst(GameStep(domino, player, steps))
        }

        // Зроблено крок
        steps++

        // Перейти до наступного гравця
        nextPlayer()
    }

    private fun nextPlayer(){
        if(currentPlayer + 1 < players.size){
            currentPlayer++
        }else{
            currentPlayer = 0
        }
    }

    private fun play(){
        val player = players[currentPlayer]// поточний гравець

        val currentLeft:Domino// фішка з лівого краю
        val currentRight:Domino// фішка з правого краю
        var suitableDominoLeft:Domino// фішка гравця, яку можна додати до лівої фішки з краю
        var suitableDominoRight:Domino// фішка гравця, яку можна додати до правої фішки з краю

        // Грати, поки в жодного з гравців не закінчилися фішки
        if(player.dominos.isNotEmpty()){
            suitableDominoLeft = Domino()
            currentLeft = table.first.domino()

            suitableDominoRight = Domino()
            currentRight = table.last.domino()

            for(domino in player.dominos){
                if(domino.rightValue == currentLeft.leftValue){
                    suitableDominoLeft = domino
                    break
                }else if(domino.leftValue == currentRight.rightValue){
                    suitableDominoRight = domino
                    break
                }
            }

            if(suitableDominoLeft.isNotEmpty()){
                nextStep(player, suitableDominoLeft, false)
                return play()
            }
            else if(suitableDominoRight.isNotEmpty()){
                nextStep(player, suitableDominoRight, true)
                return play()
            }
            else{
                // Грати, поки в резерві не закінчилися фішки
                if(getDomino(player)){
                    return play()
                }else{
                    return// в резерві закінчилися фішки
                }
            }
        }
        else{
            return// у гравця закінчилися фішки
        }
    }

    private fun findWinner(){
        var winner = players[0]

        for(player in players){
            print("\t$player - ${player.totalScore()} балів\n")

            if(player.totalScore() < winner.totalScore()){
                winner = player
            }
        }

        print("\nПЕРЕМОЖЕЦЬ: ${winner.name}")
    }
}
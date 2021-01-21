package Domino

class Game(val team:List<Player>) {
    val market= mutableListOf<Domino>()
    val table=ArrayDeque<Domino>()
    var currentPlayer=0

    fun  start(){
        initMarket()
        initPlayer()
        chooseFirstStep()
    }

    private fun chooseFirstStep() {
        TODO("Not yet implemented")
        team.get(currentPlayer).nextStep(this)
        currentPlayer++
    }

    private fun initPlayer() {
        TODO("Not yet implemented")
        for ( temp in team){
            for (i in 0..6){
             temp.handNabor.add(market.get(0))
             market.removeAt(0)
            }
        }
    }

    private fun initMarket() {
        for (i in 0..6){
            for (j in 0..6){
                var domino=Domino(i,j)
                market.add(domino)
            }
        }
        market.shuffle()
    }
}

fun main() {
    var pl1=Player("Ivan")
    var pl2=Player("Mikola")
    var pl3=Player("Petro")
    var pl4=Player("Alina")
    var list= mutableListOf<Player>(pl1,pl2,pl3,pl4)
    var game=Game(list)
    game.start()
    while(isEnd()==false){
        game.team.get(game.currentPlayer).nextStep(game);
    }
}

fun isEnd(): Boolean {
return false
}

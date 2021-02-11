package Domino

import java.util.*

fun main() {
    var player1 = Player("Ірина")
    var player2 = Player("Юлія")
    var player3 = Player("Олена")
    var player4 = Player("Аліна")

    var players = mutableListOf<Player>(player1, player2, player3, player4)
    var game = Game(players)

    game.start()
}
/*
                        ПРАВИЛА ГРИ(SCOPE):
* 1. Кожному з 4 гравців роздається по 5 фішок.
* 2. Перший крок робить гравець, що має максимальний дубль(6:6, потім 5:5 і т.д). Якщо дублів немає,
* то перший крок робить гравець з фішкою, що має максимальне значення. Далі гравці ходять по черзі(за індексом в масиві players).
* 3. Якщо у гравця немає фішки, яку можна було б покласти на стіл з лівого чи правого краю, то він може брати необмежену кількість
* фішок з резерву, поки випадково не витягне потрібну.
* 4. Гра закінчується тоді, коли в одного з учасників більше немає фішок або коли вичерпано резерв(РИБА)
* 5. Переміг той гравець, у якого взагалі немає фішок або кількість очків МІНІМАЛЬНА(якщо гра закінчена через вичерпаний резерв).
* Тобто у грі є лише один "раунд". Гравці НЕ будуть грати до 100 очків!!!
* 6. Кількість очків рахується як сума значень всіх фішок, що залишились у гравця.
* 7. Значення фішки - сума її лівої та правої сторони.
* */
class Game(private val players:List<Player>) {
    // Всі фішки
    private val reserve = mutableListOf<Domino>()

    // Хто грав якими фішками та у якому порядку ці фішки лежать на столі
    private var table = ArrayDeque<GameStep>()

    // Поточний гравець
    private var currentPlayer = -1

    // Номер поточного кроку у грі
    private var steps = 1

    // СТАРТ ГРИ
    fun start(){
        // Ініціалізація всіх об'єктів, потрібних для початку гри
        initReserve()
        initPlayer()
        printInitInfo()

        // Зробити перший крок та почати гру
        firstStep()
        play()

        // Результати гри
        getResult()
    }

    //------------ІНІЦІАЛІЗАЦІЯ ГРИ----------------
    // ГЕНЕРАЦІЯ ВСІХ ФІШОК
    private fun initReserve() {
        for (i in 0..6){
            for (j in i..6){
                var domino=Domino(i,j)
                reserve.add(domino)
            }
        }
        reserve.shuffle()
    }

    // РОЗДАТИ ГРАВЦЯМ ВИПАДКОВІ ФІШКИ
    private fun initPlayer() {
        for (player in players){
            for (i in 0..4){
                getDomino(player)
            }
        }
    }

    // ВИДАТИ ГРАВЦЮ ФІШКУ З РЕЗЕРВУ ТА З'ЯСУВАТИ, ЧИ НЕ ПУСТИЙ РЕЗЕРВ
    private fun getDomino(player: Player):Boolean{
        if(reserve.isNotEmpty()){
            player.dominoes.add(reserve.get(0))
            reserve.removeAt(0)

            return true
        }
        return false
    }

    // ОБРАТИ ПЕРШОГО ГРАВЦЯ, ПЕРШУ ФІШКУ ТА ЗДІЙСНИТИ ПЕРШИЙ КРОК ЗА ПРАВИЛАМИ ГРИ
    private fun firstStep() {
        // Перший гравець
        val firstPlayer:Player

        // Перша фішка
        val firstDomino:Domino

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

    // ЗНАЙТИ ГРАВЦІВ, У ЯКИХ Є ДУБЛІ
    private fun doublesPlayers():MutableList<Player>{
        val doublesPlayers = mutableListOf<Player>()

        for(player in players) {
            if (player.hasDoubles()) {
                doublesPlayers.add(player)
            }
        }

        return doublesPlayers
    }

    // ЗНАЙТИ ГРАВЦЯ, ЯКИЙ МАЄ МАКСИМАЛЬНИЙ ДУБЛЬ
    private fun firstByDoubles(doublesPlayers:MutableList<Player>):Player{
        var firstPlayerByDouble = doublesPlayers[0]

        for(player in doublesPlayers){
            if(player.maxDouble().totalValue() > firstPlayerByDouble.maxDouble().totalValue()){
                firstPlayerByDouble = player
            }
        }

        return firstPlayerByDouble
    }

    // ЗНАЙТИ ГРАВЦЯ, У ЯКОГО Є ФІШКА З МАКСИМАЛЬНИМ ЗНАЧЕННЯМ
    private fun firstByMaxDomino():Player{
        var firstPlayer = players[0]

        for(player in players) {
            if (player.maxDomino().totalValue() > firstPlayer.maxDomino().totalValue()) {
                firstPlayer = player
            }
        }

        return firstPlayer
    }

    // РОЗДРУКУВАТИ ІНФОРМАЦІЮ ПРО ПОЧАТКОВІ УМОВИ ГРИ
    private fun printInitInfo(){
        //Інформація про гравців на початку гри
        print("ГРАВЦІ ТА ЇХ ФІШКИ НА ПОЧАТКУ ГРИ:\n")
        players.onEach { print("\t$it\n") }

        //Інформація про резерв на початку гри
        print("\nРЕЗЕРВ НА ПОЧАТКУ ГРИ:\n\t")
        reserve.onEach { print("$it") }
    }

    //--------ХІД ГРИ------------
    // ГРА(РЕКУРСИВНА ФУНКЦІЯ)
    private fun play(){
        // Поточний гравець
        val player = players[currentPlayer]

        // Якщо у когось з гравців закінчилися фішки, то завершити гру
        players.onEach { if(it.dominoes.isEmpty()) return }

        // Фішка гравця, яку можна покласти на стіл до лівої фішки з краю
        val suitableDominoLeft = player.addDominoToLeft(table.first.domino)

        if(suitableDominoLeft == null){ // Якщо нічого не можна покласти з лівої сторони, то перевірити праву сторону
            // Фішка гравця, яку можна покласти на стіл до правої фішки з краю
            val suitableDominoRight = player.addDominoToRight(table.last.domino)

            when {
                suitableDominoRight != null -> {
                    // Якщо знайдено необхідну фішку, то зробити крок цією фішкою та продовжити гру
                    nextStep(player, suitableDominoRight, true)
                    return play()
                }
                getDomino(player) -> {
                    // Якщо не можна зробити крок, то взяти одну випадкову фішку з резерву та продовжити гру
                    return play()
                }
                else -> {
                    // Резерв вичерпано - ГРА ЗАВЕРШЕНА
                    return
                }
            }
        }else{
            // Якщо знайдено необхідну фішку, то зробити крок цією фішкою та продовжити гру
            nextStep(player, suitableDominoLeft, false)
            return play()
        }
    }

    // ЗДІЙСНИТИ НАСТУПНИЙ КРОК
    private fun nextStep(player: Player, domino: Domino, toRight:Boolean){
        // Забрати ту фішку, якою ходить гравець
        player.dominoes.remove(domino)

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

    // З'ЯСУВАТИ, ХТО МАЄ ЗРОБИТИ КРОК
    private fun nextPlayer(){
        if(currentPlayer + 1 < players.size){
            currentPlayer++
        }else{
            currentPlayer = 0
        }
    }

    //--------ЗАВЕРШЕННЯ ГРИ------------
    // ВИВЕСТИ РЕЗУЛЬТАТИ ГРИ
    private fun getResult(){
        // Інформація про фішки на столі
        print("\n\nСТІЛ:\n\t")
        table.onEach { print("${it.domino}") }

        // Інформація про резерв
        print("\n\nРЕЗЕРВ В КІНЦІ ГРИ:\n\t")
        if(reserve.isEmpty()){
            print("РИБА!")
        }else{
            reserve.onEach { print(it) }
        }

        // Інформація про переможця
        findWinner()

        // Інформація про всіх гравців
        print("\n\nФІШКИ, ЯКІ ЗАЛИШИЛИСЬ У ГРАВЦІВ В КІНЦІ ГРИ:\n")
        players.onEach { print("\t$it\n") }

        // Історія гри
        print("\nІСТОРІЯ ГРИ(РОЗКЛАДЕНІ ФІШКИ, ГРАВЕЦЬ(ЯКИЙ ДОДАВ ФІШКУ) І КРОК(НА ЯКОМУ ДОДАЛИ ФІШКУ):\n")
        table.onEach { print("\t$it\n") }
    }

    // ВИЗНАЧИТИ ПЕРЕМОЖЦЯ
    private fun findWinner(){
        var winner = players[0]

        for(player in players){
            if(player.dominoes.isEmpty()){
                // Переможець не має жодних фішок
                winner = player
                break
            }else if(player.totalScore() < winner.totalScore()){
                // Переможець має МІНІМАЛЬНУ кількість очків(у випадку РИБИ)
                winner = player
            }
        }

        print("\n\nПЕРЕМОЖЕЦЬ:\n\t${winner.name}")
    }
}
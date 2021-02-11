package Domino

class Player(val name:String){
    val dominoes = mutableListOf<Domino>()

    // Загальна сума значень всіх фішок, що є у гравця
    fun totalScore(): Int{
        var score = 0

        for(domino in dominoes){
            score += domino.totalValue()
        }

        return score
    }

    // Фішка з максимальним значенням
    fun maxDomino():Domino {
        var max = dominoes[0]

        for (domino in dominoes) {
            if ((domino.leftValue + domino.rightValue) > (max.leftValue + max.rightValue)) {
                max = domino
            }
        }

        return max
    }

    // Чи є у гравця дублі
    fun hasDoubles():Boolean{
        var doubles = findDoubles()
        return doubles.isNotEmpty()
    }

    // Максимальний дубль, який є у даного гравця
    fun maxDouble():Domino{
        var doubles = findDoubles()
        var max = doubles[0]

        for(double in doubles){
            if(double.leftValue > max.leftValue) max = double
        }

        return max
    }

    // Знайти дублі
    private fun findDoubles():MutableList<Domino>{
        var doubles = mutableListOf<Domino>()

        for(domino in dominoes){
            if(domino.isDouble()) doubles.add(domino)
        }

        return doubles
    }

    // Чи може гравць покласти одну з фішок з лівої сторони стола. Якщо ні - поверне null. Інакше поверне необхідну фішку.
    public fun addDominoToLeft(currentLeft:Domino): Domino? {
        var suitableDominoLeft:Domino? = null

        for(domino in dominoes){
            for(i in 1..2){
                domino.rotate()
                if(domino.rightValue == currentLeft.leftValue){
                    suitableDominoLeft = domino
                    break
                }
            }
        }

        return suitableDominoLeft
    }

    // Чи може гравць покласти одну з фішок з правої сторони стола. Якщо ні - поверне null. Інакше поверне необхідну фішку.
    public fun addDominoToRight(currentRight:Domino):Domino?{
        var suitableDominoRight:Domino? = null

        for(domino in dominoes){
            for(i in 1..2){
                domino.rotate()
                if(domino.leftValue == currentRight.rightValue){
                    suitableDominoRight = domino
                    break
                }
            }
        }

        return suitableDominoRight
    }

    override fun toString(): String {
        return "Ім'я: '$name', фішки: $dominoes"
    }
}
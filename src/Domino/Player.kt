package Domino

class Player(val name:String){
    val dominos = mutableListOf<Domino>()

    override fun toString(): String {
        return "Ім'я: '$name', фішки: $dominos"
    }

    fun totalScore(): Int{
        var score = 0

        for(domino in dominos){
            score += domino.totalValue()
        }

        return score
    }

    fun maxDomino():Domino {
        var max = dominos[0]

        for (domino in dominos) {
            if ((domino.leftValue + domino.rightValue) > (max.leftValue + max.rightValue)) {
                max = domino
            }
        }

        return max
    }

    fun hasDoubles():Boolean{
        var doubles = findDoubles()
        return doubles.isNotEmpty()
    }

    fun maxDouble():Domino{
        var doubles = findDoubles()
        var max = doubles[0]

        for(double in doubles){
            if(double.leftValue > max.leftValue){
                max = double
            }
        }

        return max
    }

    private fun findDoubles():MutableList<Domino>{
        var doubles = mutableListOf<Domino>()

        for(domino in dominos){
            if(domino.isDouble()) doubles.add(domino)
        }

        return doubles
    }
}
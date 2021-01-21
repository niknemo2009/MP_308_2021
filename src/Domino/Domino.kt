package Domino

class Domino(val leftValue:Int, val rightValue:Int) {
    // Для створення пустого об'єкта
    constructor() : this(-1, -1) {}

    override fun toString(): String {
        return "| $leftValue : $rightValue |"
    }

    fun totalValue():Int{
        return  leftValue + rightValue
    }

    fun isDouble():Boolean{
        return leftValue == rightValue
    }

    fun isNotEmpty():Boolean{
        return leftValue != -1 && rightValue != -1
    }
}
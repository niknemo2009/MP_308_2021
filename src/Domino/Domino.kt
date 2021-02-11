package Domino

class Domino(var leftValue:Int, var rightValue:Int) {
    // Значення фішки
    fun totalValue():Int{
        return  leftValue + rightValue
    }

    // Чи дорівнює ліва і права частина доміно(дубль)
    fun isDouble():Boolean{
        return leftValue == rightValue
    }

    // Повернути доміно на 180 градусів
    fun rotate(){
        leftValue = rightValue.also { rightValue = leftValue }
    }

    override fun toString(): String {
        return "| $leftValue : $rightValue |"
    }
}
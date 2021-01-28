package domino

class Domino(var leftValue: Int, var rightValue: Int) {

    fun isDouble(): Boolean {
        return leftValue == rightValue
    }

    override fun toString(): String {
        return "[$leftValue;$rightValue]"
    }

    fun getValue(): Int {
        return leftValue + rightValue
    }

    fun rotate180(){
        val temp = leftValue
        leftValue = rightValue
        rightValue = temp
    }

}
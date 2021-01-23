package domino

class Domino(private var leftValue: Int, private var rightValue: Int) {

    fun isDouble(): Boolean {
        return leftValue == rightValue
    }

    override fun toString(): String {
        return "[$leftValue;$rightValue]"
    }

    fun getValue(): Int {
        return leftValue + rightValue
    }

}
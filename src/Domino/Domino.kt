package Domino

class Domino(val leftValue:Int,val rightValue:Int) {
    override fun toString(): String {
        return "| $leftValue -- $rightValue |"
    }
}
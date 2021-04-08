package Task_08_04_2021

fun main() {
    // Завдання №1
    print("TASK #1\n")
    val condition1 = object : Condition {
        override fun filter(digit: Int): Boolean {
            return  digit > 3
        }
    }
    viewArray(arrayOf(1, 2, 3, 4, 5), condition1)

    // Завдання №2
    print("\nTASK #2\n")
    val condition2 = object : Change {
        override fun transform(digit: Int): Int {
            return digit * 2
        }
    }
    val arr = arrayOf(1, 2, 3, 4, 5)
    changeArray(arr, condition2)
    arr.forEach { print("$it ") }
}

fun viewArray(arr:Array<Int>, condition: Condition){
    for(i in arr){
        if(condition.filter(i)){
            print("$i\n")
        }
    }
}

fun changeArray(arr:Array<Int>, change: Change){
    for(i in arr.indices){
        arr[i] = change.transform(arr[i])
    }
}

interface Condition{
    fun filter(digit:Int):Boolean
}

interface Change{
    fun transform(digit:Int):Int
}
package Task_18_02_2021

import java.lang.Integer.max
import kotlin.math.abs

fun main() {
    //1. Послідовність елементів з максимальним значенням
    val arr = arrayOf(2, -2, -5, 5, 6, 7)
    val result = maxChain(arr)
    print("Послідовність з максимальним значенням: $result")

    //2. Найменше спільне кратне
    val first = 6
    val second = 4
    val lcm = leastCommonMultiple(first, second)
    print("\nНайменше спільне кратне $first та $second: $lcm")
}

// Знайти послідовність елементів з максимальним значенням
fun maxChain(arr:Array<Int>):MutableList<Int>{
    val result = mutableListOf<Int>()

    for(elem in arr){
        if(elem > 0) result.add(elem)
    }

    return result
}

//Алгоритм Евкліда (найменше спільне кратне = добуток чисел / найбільший спільний дільник)
fun leastCommonMultiple(first:Int, second:Int):Int{
    var gcd = 1

    for (i in 1..max(first, second)) {
        if(first % i == 0 && second % i == 0) gcd = i
    }

    return abs(first * second) / gcd
}
package Task_25_03_2021

fun main() {
    // Завдання №1
    print("TASK #1\n")
    val range1 = 1..4
    val range2 = 2..5
    val range3 = 3..7

    val same = crossRange(range1, range2, range3)
    print(same)

    // Завдання №2
    print("\n\nTASK #2\n")
    val words = arrayOf("abc", "sd", "abc", "sd", "abc", "pc")
    val result = frequency(words)
    print(result)
}

// Метод, що приймає будь-яку кількість інтервалів. Знайти спільні елементи у всіх інтервалів
fun crossRange(vararg ranges: IntRange):Set<Int>{
    var result = setOf<Int>()

    if(ranges.size > 1){
        // 1. Перший перетин
        result = ranges[0] intersect ranges[1]

        //2. Наступні перетини
        for(i in 2 until ranges.size){
            result = ranges[i] intersect result
        }
    }

    return result
}

// Знайти частоту слів в масиві
fun frequency(strings: Array<String>):MutableMap<String, Int>{
    val stat = mutableMapOf<String, Int>()

    for(line in strings){
        if(stat.contains(line)){
            var quantity = stat[line] as Int
            quantity++
            stat.put(line, quantity)
        }else{
            stat.put(line, 1)
        }
    }

    return stat
}
package Task_22_04_2021

fun main() {
    // Завдання №1
    print("TASK #1\n")
    val result1 = changeLetter("abcadf")
    print(result1)

    // Завдання №2
    print("\n\nTASK #2\n")
    val arr1 = arrayOf(1, 2, 3, 4, 5)
    val condition1 = object : Condition{
        override fun change(digit: Int): Int {
            return  digit * digit
        }
    }
    val result2 = convertArr(arr1, condition1)
    result2.forEach { print("$it ") }

    // Завдання №3
    print("\n\nTASK #3\n")
    val result3 = getMaxSumValue(45..55)
    print(result3)

    // Завдання №4
    print("\n\nTASK #4\n")
    val result4 = uniqueLetters("fgabd", "wabh")
    print(result4)
}

// Замінити літеру
fun changeLetter(string: String):String{
    val sb = StringBuffer("")

    for(elem in string){
        if (elem != 'a') sb.append(elem) else sb.append('o')
    }

    return  sb.toString()
}

// Змінити елементи масиву (передавати умову конвертації)
fun convertArr(arr: Array<Int>, condition: Condition):Array<Int>{
    for(i in arr.indices){
        arr[i] = condition.change(arr[i])
    }

    return arr
}

@FunctionalInterface
interface Condition{
    fun change(digit:Int):Int
}

// Діапазон чисел - знайти число з максимальною сумою складових
fun getMaxSumValue(range: IntRange):Int{
    // 1. Знайти всі суми
    val sums = mutableMapOf<Int, Int>()
    for (elem in range){
        var sum = 0
        var temp = elem

        do{
            sum += temp % 10
            temp /= 10
        }while (temp % 10 > 0)

        sums[elem] = sum
    }

    // 2. Знайти максимальну пару ключ-значення
    var maxEntry = sums.entries.elementAt(0)
    for(entry in sums){
        if(entry.value > maxEntry.value){
            maxEntry = entry
        }
    }

    return maxEntry.key
}

// Передається 2 string вивести ті літери, що відсутні в одному
fun uniqueLetters(str1: String, str2: String):Set<Char>{
    val letters1 = str1.toSet()
    val letters2 = str2.toSet()

    return (letters1 subtract  letters2) union (letters2 subtract  letters1)
}

package Task_01_04_2021

fun main() {
    // Завдання №1
    print("TASK #1\n")
    val arr1 = arrayOf(2, 1, 5, 7, 9, 4)
    val result = missedElements(arr1)
    print(result)

    // Завдання №2
    print("\n\nTASK #2\n")
    val arr2 = arrayOf(1, 2, 3, 0, 4, 2, 5)
    findSum(arr2, 4)

    // Завдання №3
    print("\nTASK #3\n")
    print("KOtlIn".convert())

    // Завдання №4
    print("\n\nTASK #4\n")
    val arr3 = arrayOf(1, 4, 3, 4, 5, 8, 0)
    findSumInArr(arr3, 8)
}

// Метод приймає масив Int. Знайти, які едементи відсутні
fun missedElements(arr: Array<Int>):MutableSet<Int>{
    var result = mutableSetOf<Int>()
    arr.sort()

    val range = arr[0]..arr[arr.size -1]
    for(elem in range){
        if(arr.indexOf(elem) == -1) result.add(elem)
    }

    return result
}

// Знайти пару елементів масиву, сума яких відповідає другому аргументу
fun findSum(arr: Array<Int>, sum: Int){
    var count = 0

    for(i in 0..arr.size - 2){
        val elem = arr[i]
        for(j in (i + 1) until arr.size){
            if(elem + arr[j] == sum){
                print("$elem + ${arr[j]} = $sum\n")
                count++
            }
        }
    }

    if(count == 0) print("Таких елементів немає!")
}

// Функція розширення для String, що конвертує регістри
fun String.convert():String {
    var result = ""

    for(char in this){
        var newChar: Char
        if(char.isUpperCase()) newChar = char.toLowerCase() else newChar = char.toUpperCase()
        result += newChar
    }

    return result
}

// Знайти довільну кількість елементів, що в сумі дають другий параметр
// ЛИШЕ ДЛЯ ТИХ, ЩО ЙДУТЬ ПІДРЯД
fun findSumInArr(arr: Array<Int>, sum: Int){
    var count = 0
    val allChains = allChainsOf(arr)

    for(chain in allChains){
        if(chain.sum() == sum && chain.size > 1){
            print("$chain\n")
            count++
        }
    }

    if(count == 0) print("Таких послідовностей немає!")
}

// Всі можливі послідовності у масиві
fun allChainsOf(arr:Array<Int>):MutableList<List<Int>> {
    val allChains = mutableListOf<List<Int>>()
    var i = 0

    while(i < arr.size){
        var j = arr.size - 1
        while (j >= i){
            val chain = arr.slice(i..j)
            allChains.add(chain)
            j--
        }
        i++
    }

    return allChains
}




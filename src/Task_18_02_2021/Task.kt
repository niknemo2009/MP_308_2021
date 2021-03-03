package Task_18_02_2021

fun main() {
    //1. Знайти послідовність елементів з максимальною сумою
    val arr = arrayOf(2, -2, -5, 5, 6, 7)
    val result = maxChain(arr)
    print("Послідовність з максимальною сумою: $result")

    //2. Знайти найменше спільне кратне
    val first = 2
    val second = 4
    val lcm = leastCommonMultiple(first, second)
    print("\nНайменше спільне кратне діапазону від $first до $second: $lcm")
}

// Послідовність елементів з максимальною сумою
fun maxChain(arr:Array<Int>):List<Int>{
    val allChains = allChainsOf(arr)
    var maxChain = allChains[0]

    for(chain in allChains){
        if(chain.sum() > maxChain.sum()) maxChain = chain
    }

    return maxChain
}

// Найменше спільне кратне для діапазону
fun leastCommonMultiple(start:Int, end:Int):Int?{
    val size = (start..end).count() // кількість елементів в діапазоні

    // 1. Валідація параметрів
    if(end < start){
        print("\nERROR: Неприпустимі аргументи для функції 'leastCommonMultiple'! Аргумент 'end' менший за 'start'")
        return null
    }else if((start..end).contains(0)){
        print("\nERROR: Неприпустимі аргументи для функції 'leastCommonMultiple'! Діапазон не може містити 0")
        return null
    }

    // 2. Пошук НСК
    var number = 1
    while (true){
        var count = 0 // на яку кількість елементів у діапазоні ділиться число

        for(elem in start..end){
            if(number % elem == 0) count++
        }

        // Якщо число ділиться на ВСІ елементи діапазона, то НСК знайдено
        if(count == size) break

        number++
    }

    return number
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
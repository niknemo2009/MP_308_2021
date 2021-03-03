package Task_25_02_2021

fun main() {
    // Завдання №1
    val arr1 = arrayOf(1, 4, 2)
    val arr2 = arrayOf(1, 5, 3)
    val pairs = findPairs(arr1, arr2)
    print("Елементи масивів:\n")
    pairs.forEach{ print("$it\n")}

    // Завдання №2
    var arr3 = arrayOf('a', '9', 'b', 'c', '4', 'd')
    val maxBalancedChain = findMaxBalancedChain(arr3)
    print("\nМаксимальний збалансований ланцюжок: $maxBalancedChain")
}

// Знайти елементи, після перестановки яких буде однакова сума у масивах
fun findPairs(arr1:Array<Int>, arr2:Array<Int>):MutableList<MutableList<Int>>{
    val pairs = mutableListOf<MutableList<Int>>()

    // Знайти суми
    val firstSum = arr1.sum()
    val secondSum = arr2.sum()

    for(elem1 in arr1){
        for (elem2 in arr2){
            val pair = mutableListOf<Int>()

            if((firstSum - elem1 + elem2) == (secondSum - elem2 + elem1)){
                pair.add(elem1)
                pair.add(elem2)
                pairs.add(pair)
            }
        }
    }

    return pairs
}

// Знайти максимальний збалансований ланцюжок (однакова кількість літер і цифр)
fun findMaxBalancedChain(arr:Array<Char>):MutableList<Char>{
    var maxBalancedChain = mutableListOf<Char>()

    //1. Знайти всі можливі послідовності
    val chains = allChainsOf(arr)

    //2. Знайти всі збалансовані послідовності
    val balancedChains = mutableListOf<MutableList<Char>>()
    chains.forEach{ if(isBalancedChain(it)) balancedChains.add(it) }

    //3. Знайти максимальну збалансовану послідовність
    maxBalancedChain = findMaxChain(balancedChains)

    return maxBalancedChain
}

// Всі можливі послідовності
fun allChainsOf(arr:Array<Char>):MutableList<MutableList<Char>> {
    val allChains = mutableListOf<MutableList<Char>>()

    var i = 0
    while(i < arr.size){
        var j = arr.size - 1
        while (j >= i){
            val chain = arr.slice(i..j)
            allChains.add(chain.toMutableList())
            j--
        }
        i++
    }

    return allChains
}

// Чи дорівнює кількість букв в послідовності кількості цифр
fun isBalancedChain(chain: MutableList<Char>):Boolean{
    var digits = 0
    var chars = 0

    for(elem in chain){
        if(elem.isDigit()) digits++
        if(elem.isLetter()) chars++
    }

    return digits == chars
}

// Послідовність максимальної довжини
fun findMaxChain(chains: MutableList<MutableList<Char>>):MutableList<Char>{
    var maxChain = chains[0]

    for(chain in chains){
        if(chain.size > maxChain.size){
            maxChain = chain
        }
    }

    return maxChain
}
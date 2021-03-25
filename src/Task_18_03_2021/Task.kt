package Task_18_03_2021

fun main() {
    // Завдання №1
    print("TASK #1\n")
    val arr = arrayOf("Hello", "World")
    val maxString = findMaxString(arr)
    print("Result is: '$maxString'")

    //Завдання №2
    print("\n\nTASK #2\n")
    val quantity = findQuantityOfWords("bbcaacbbbbcaacffbb", "abbc")
    print("Quantity: $quantity")
}

// Знайти рядок, що має максимальну суму char(ASCII)
fun findMaxString(arr:Array<String>):String{
    var maxString = arr[0]

    // Знайти суму char
    for(row in arr){
        val sum = stringSum(row)
        if(sum > stringSum(maxString)) maxString = row
    }

    return maxString
}

fun stringSum(row: String):Int{
    var sum = 0
    row.onEach { sum += it.toInt() }
    return sum
}

// Скільки разів можемо написати літерами одного слова інше слово?
fun findQuantityOfWords(word1: String, word2: String): Int{
    var result = 0
    val source = letters(word1)
    print("${source}\n")//for testing
    val destination = letters(word2)

    // Скільки слів(destination) можна зібрати з джерела(source)?
    while (allSymbolsPresent(source)){
        var letters = 0

        // "Написати" слово, прибираючі символи з source
        source.onEach {
            if(destination.contains(it.key)){
                val newQuantity  = it.value - destination.get(it.key) as Int
                if(newQuantity >= 0){
                    source.put(it.key, newQuantity)
                    letters++
                }
            }
        }

        print("${source}\n")//for testing

        // Якщо записали всі букви, то слово написано повністю
        if(letters == destination.size){
            result++
        }
    }

    return result
}

// Скільки разів кожен символ зустрічається у слові?
fun letters(word: String):MutableMap<Char, Int>{
    val components = mutableMapOf<Char, Int>()

    for(ch in word){
        if(components.contains(ch)){
            var quantity = components[ch] as Int
            quantity++
            components.put(ch, quantity)
        }else{
            components.put(ch, 1)
        }
    }

    return components
}

// Чи всі символи доступні?
fun allSymbolsPresent(symbols: MutableMap<Char, Int>):Boolean{
    for(pair in symbols){
        if(pair.value <= 0) return false
    }

    return true
}
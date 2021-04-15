package Task_15_04_2021

fun main() {
    // Завдання №1
    print("TASK #1\n")
    val arr1 = arrayOf(245, 1456, 450, 45, 9, 380)
    val maxElement = getSequence(arr1, 45)
    print(maxElement)

    // Завдання №2
    print("\n\nTASK #2\n")
    val arr2 = arrayOf(1, 3, 4, 10, 2, 5)
    val arr3 = arrayOf(6, 7, 8, 10, 2, 5)
    val arr4 = arrayOf(6, 7, 8, 10, 2, 15)
    val maxCommonElement = commonElements<Int>(arr2, arr3, arr4)
    print(maxCommonElement)
}

// Метод приймає масив Int та послідовність цифр. Знайти елементи, що мають таку послідовність
fun getSequence(arr: Array<Int>, sequence: Int):Int?{
    val result = mutableListOf<Int>()

    for(elem in arr){
        if(elem.toString().contains(sequence.toString())) result.add(elem)
    }

    return result.max()
}

// Приймає змінну кількість масивів. Є обмеження: повинні реалізовувати інтерфейс comparable. Узагальнений метод.
// Знайти найбільший спільний елемент
fun <T:Comparable<T>>commonElements(vararg arrays: Array<T>):T?{
    var common = mutableListOf<T>()

    if(arrays.isEmpty()){
        return null
    }

    arrays[0].forEach { common.add(it)}
    for(index in arrays.indices){
        val temp = mutableListOf<T>()

        for(i in common.indices){
            if(arrays[index].contains(common[i])){
                temp.add(common[i])
            }
        }

        common = temp
    }

    return common.max()
}

import java.util.function.IntPredicate

class Example1 {
}

fun main() {
  var mas= arrayOfNulls<Int>(5)
var result=sumArray(mas)
    var qwe:Int;
    val listWithNulls: MutableList<Int> = mutableListOf(4)
    listWithNulls.add(34)
    val even: IntArray = intArrayOf(2, 4, 6)
    val odd: IntArray = intArrayOf(1, 3, 5)

    val lala: Array<IntArray> = arrayOf(even, odd)
  var result12=0
    for ( row in lala){
        for (temp in row){
           result12+=temp
        }
    }


    println("resultat = $result")
//    var con1=IntPredicate {it%2==0  }
//    var con2=IntPredicate {  }
//    println(sumForCondition(con1,mas))
}

fun sumArray(arr:Array<Int?>):Int{
    var result=0
    for(temp in arr){
      result=if(temp!=null) result+temp else 0
    }
    return result;

}

fun sumForCondition(condition :IntPredicate,arr:Array<Int>):Int{
    var result=0
    for(temp in arr){
        if(condition.test(temp)){
            result+=temp
        }

    }
    return result;

}



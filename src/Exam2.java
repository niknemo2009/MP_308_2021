import java.util.Arrays;
import java.util.List;

public class Exam2 {
    public static void main(String[] args) {
        int[] qwe={1,2,3};
         var  numbers  = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println(sum);
    }
}

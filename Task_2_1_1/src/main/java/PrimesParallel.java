import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimesParallel implements Prime{
    private final List<Integer> numbers;
    public PrimesParallel(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
    }

    public boolean compute() {
        long answ = this.numbers
                .parallelStream()
                .filter(PrimeCheck::isPrime)
                .count();
        return answ > 0;
    }
    public static void main(String[] args) {
//        Integer[] numbers = new Integer[] {6, 8, 7, 13, 5, 9, 4};
        Integer[] numbers = new Integer[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        PrimesParallel primesParallel = new PrimesParallel(Arrays.asList(numbers));
        boolean answ = primesParallel.compute();
        System.out.println(answ);
    }

}

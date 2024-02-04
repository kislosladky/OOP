import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class PrimesParallel {
    private boolean anyNonPrimes = false;
    private static boolean notPrime(int toCheck) {
        int sqrt = (int) Math.sqrt(toCheck);
        for (int i = 2; i <= sqrt; i++) {
            if (toCheck % i == 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        Integer[] numbers = new Integer[] {6, 8, 7, 13, 5, 9, 4};
        Integer[] numbers = new Integer[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        PrimesParallel primesParallel = new PrimesParallel();
        long answ = new ArrayList<>(Arrays.asList(numbers))
                .parallelStream()
                .filter(PrimesParallel::notPrime)
                .count();
        System.out.println(answ > 0);
    }

}

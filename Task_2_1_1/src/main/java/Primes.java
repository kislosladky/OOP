import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Primes {

    private boolean compute(List<Integer> numbers) {
        for (Integer num : numbers) {
            if (!PrimeCheck.isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(6);
        ints.add(8);
        ints.add(7);
        ints.add(13);
        ints.add(5);
        ints.add(9);
        ints.add(4);
//        ints.add(20319251);
//        ints.add(6997901);
//        ints.add(6997927);
//        ints.add(6997937);
//        ints.add(17858849);
//        ints.add(6997967);
//        ints.add(6998009);
//        ints.add(6998029);
//        ints.add(6998039);
//        ints.add(20165149);
//        ints.add(6998051);
//        ints.add(6998053);
//        for (int i = 0; i < length; i++) {
//            ints.add(scanner.nextInt());
//        }
        Primes primes = new Primes();
        System.out.println(primes.compute(ints));
    }

}

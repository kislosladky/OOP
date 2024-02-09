import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Primes implements Prime{
    private List<Integer> numbers;
    public Primes(List<Integer> numbers) {
        this.numbers = numbers;
    }
    public boolean compute() {
        for (Integer num : this.numbers) {
            if (!PrimeCheck.isPrime(num)) {
                return true;
            }
        }
        return false;
    }
}

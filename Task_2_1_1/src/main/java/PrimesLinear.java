import java.util.List;

/**
 * This class searches non-prime number using naive linear algorithm.
 */
public class PrimesLinear implements Prime {
    private final List<Integer> numbers;

    public PrimesLinear(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public boolean compute() {
        for (Integer num : this.numbers) {
            if (Prime.notPrime(num)) {
                return true;
            }
        }
        return false;
    }
}

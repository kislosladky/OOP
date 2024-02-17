import java.util.List;

/**
 * This class searches non-prime number using naive linear algorithm.
 */
public class PrimesLinear implements Prime {
    private final List<Integer> numbers;

    public PrimesLinear(List<Integer> numbers) {
        this.numbers = numbers;
    }

    /**
     * The main function of the class.
     *
     * @return true if the list contains non-prime number.
     */
    public boolean compute() {
        for (Integer num : this.numbers) {
            if (Prime.notPrime(num)) {
                return true;
            }
        }
        return false;
    }
}

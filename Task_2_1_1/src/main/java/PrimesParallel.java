import java.util.ArrayList;
import java.util.List;

/**
 * This class searches for non-prime number using parallel stream.
 */
public class PrimesParallel implements Prime {
    private final List<Integer> numbers;

    public PrimesParallel(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
    }

    /**
     * Searches for non-prime number.
     *
     * @return true if the list contains non-prime number.
     */
    public boolean compute() {
        return this.numbers
                .parallelStream()
                .anyMatch(Prime::notPrime);
    }
}

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
        return answ != this.numbers.size();
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * This class searches for non-prime number by splitting the list into
 * "numberOfThreads" parts and searching in parallel using java.lang.Thread.
 */
public class PrimesThread implements Prime {
    private volatile Boolean anyNonPrime = false;
    private final List<Integer> numbers;
    private final int numberOfThreads;
    private List<Thread> threads;

    public PrimesThread(int numberOfThreads, List<Integer> numbers) {
        this.numberOfThreads = numberOfThreads;
        this.numbers = numbers;
    }

    /**
     * A subclass that we put in threads.
     */
    class PrimeThread implements Runnable {
        private final List<Integer> toCheck;

        public PrimeThread(List<Integer> toCheck) {
            this.toCheck = toCheck;
        }

        private void interruptAll() {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }

        @Override
        public void run() {
            for (Integer numberToCheck : toCheck) {
                if (Prime.notPrime(numberToCheck)) {
                    anyNonPrime = true;
                    interruptAll();
                    break;
                }
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        }
    }

    /**
     * The main function that searches for non-prime number by threading.
     *
     * @return true if the list contains non-prime number.
     */
    public boolean compute() {
        int lengthOfSubarray = numbers.size() / this.numberOfThreads;
        int rest = numbers.size() % this.numberOfThreads;
        this.threads = new ArrayList<>();
        for (int i = 0; i < this.numberOfThreads; i++) {
            List<Integer> toCheck =
                    new ArrayList<>(numbers.subList(lengthOfSubarray * i,
                            lengthOfSubarray * (i + 1)));
            if (rest > i) {
                toCheck.add(numbers.get(lengthOfSubarray * this.numberOfThreads + i));
            }

            Thread thread = new Thread(new PrimeThread(toCheck));
            thread.start();
            threads.add(thread);
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Calculating is interrupted");
        }

        return anyNonPrime;
    }
}

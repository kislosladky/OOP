import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimesThread implements Prime{
    private volatile Boolean anyNonPrime = false;
    private final List<Integer> numbers;
    private final int numberOfThreads;
    public PrimesThread(int numberOfThreads, List<Integer> numbers) {
        this.numberOfThreads = numberOfThreads;
        this.numbers = numbers;
    }

    class PrimeThread implements Runnable {
        private final List<Integer> toCheck;

        public PrimeThread(List<Integer> toCheck) {
            this.toCheck = toCheck;
        }


        @Override
        public void run() {
            for (Integer numberToCheck : toCheck) {
                if (!PrimeCheck.isPrime(numberToCheck)) {
                    anyNonPrime = true;
                    break;
                }
            }
        }
    }

    public boolean compute() {
        int lengthOfSubarray = numbers.size() / this.numberOfThreads;
        int rest = numbers.size() - lengthOfSubarray * this.numberOfThreads;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < this.numberOfThreads; i++) {
            List<Integer> toCheck =
                    new ArrayList<>(numbers.subList(lengthOfSubarray * i,
                            lengthOfSubarray * (i + 1)));
            if (rest > i)
                toCheck.add(numbers.get(lengthOfSubarray * this.numberOfThreads + i));

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


    public static void main(String[] args) {
//        Integer[] numbers = new Integer[] {6, 8, 7, 13, 5, 9, 4};
//        Integer[] numbers = new Integer[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
//                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        Integer[] numbers = new Integer[100000];
        Arrays.fill(numbers, 20165149);
        int numberOfThreads = 8;
        List<Integer> toCheck = new ArrayList<>(Arrays.asList(numbers));
        PrimesThread primesThread = new PrimesThread(numberOfThreads, toCheck);
        boolean answ = primesThread.compute();

        System.out.println(answ);
    }


}

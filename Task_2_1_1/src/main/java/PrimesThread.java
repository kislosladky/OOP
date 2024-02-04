import java.util.concurrent.atomic.AtomicInteger;

public class PrimesThread {
    private volatile boolean anyNonPrime = false;
    private volatile AtomicInteger numberOfFreeThreads = new AtomicInteger();
    private final int[] numbers;
    public PrimesThread(int[] numbers) {
        this.numbers = numbers;
    }

    class PrimeCheck implements Runnable {
        private final int toCheck;

        public PrimeCheck(int toCheck) {
            this.toCheck = toCheck;
        }

        @Override
        public void run() {
            numberOfFreeThreads.decrementAndGet();
            int sqrt = (int) Math.sqrt(toCheck);
            for (int i = 2; i <= sqrt; i++) {
                if (toCheck % i == 0) {
                    anyNonPrime = true;
                    break;
                }
            }
            numberOfFreeThreads.incrementAndGet();
        }
    }

    private boolean compute(int numberOfThreads) {
        this.numberOfFreeThreads.getAndSet(numberOfThreads);
        for (int i = 0; i < numbers.length; i++) {
            while (this.numberOfFreeThreads.get() == 0) {}
            new Thread(new PrimeCheck(numbers[i])).start();
        }
        return anyNonPrime;
    }


    public static void main(String[] args) {
//        int[] numbers = new int[] {6, 8, 7, 13, 5, 9, 4, 36, 36,36,251,1525,66, 262,6626,3636,37,89,69,0,70,8,15,25,326,36,23,3,3,3,345,5,6,6,6,6546565,656,254,568,689,356,235,457,578};
        int[] numbers = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        int numberOfThreads = 4;

        PrimesThread primesThread = new PrimesThread(numbers);

        boolean answ = primesThread.compute(numberOfThreads);

        System.out.println(answ);
    }


}

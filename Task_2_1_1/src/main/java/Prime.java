/**
 * Interface for classes that check if the list contains non-prime number.
 */
@FunctionalInterface
public interface Prime {
    /**
     * The main function of this interface.
     *
     * @return the presence of the non-prime number.
     */
    boolean compute();

    /**
     * This function checks a single number if it's non-prime.
     *
     * @param numberToCheck is the number we are checking.
     *
     * @return true if the number is non-prime.
     */
    static boolean notPrime(Integer numberToCheck) {
        int sqrt = (int) Math.sqrt(numberToCheck);
        for (int i = 2; i <= sqrt; i++) {
            if (numberToCheck % i == 0) {
                return true;
            }
        }
        return false;
    }
}

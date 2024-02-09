public class PrimeCheck {

    public static boolean isPrime(Integer numberToCheck) {
        int sqrt = (int) Math.sqrt(numberToCheck);
        for (int i = 2; i <= sqrt; i++) {
            if (numberToCheck % i == 0) {
                return false;
            }
        }
        return true;
    }

}

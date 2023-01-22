package Solutions;

import Utility.Primes;

public class Problem3 {
    public static int solution() {
        final long number = 600851475143l;
        final long limit = (long) Math.sqrt(number);

        int largestPrime = 2;
        for (int candidate = 3; candidate < limit; candidate += 2) {
            // First check if the candidate is a factor
            if (number % candidate == 0) {
                if (Primes.isPrime(candidate)) {
                    largestPrime = candidate;
                }
            }
        }

        // largestPrime now contains the largest found prime
        return largestPrime;
    }
}

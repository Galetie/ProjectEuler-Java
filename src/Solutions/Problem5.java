package Solutions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Problem5 {
    public static int solution() {
        return solutionB(20);
    }

    /*
     * Solution A implements the brute forced non-mathematical approach to find the correct solution
     * We simply check every multiple of 20 in order to avoid checking the most obvious non-answers
     * and then check their divisibility
     */
    private static int solutionA() {
        // Simply check every multiple of 20 to cut out the most numbers
        int divisor;
        for (long candidate = 20; true; candidate += 20) {
            // Check that candidate divides into each number below it
            for (divisor = 19; divisor > 1; divisor--) {
                if (candidate % divisor != 0) {
                    break;
                }
            }

            // Check if the candidate did indeed divide into all the numbers
            if (divisor == 1) {
                return (int) candidate;
            }
        }
    }

    private static int solutionB(int max) {
        int currentLCM = 1;

        for (int b = 3; b < max; b++) {
            currentLCM = lcm(currentLCM, b);
        }

        return currentLCM;
    }

    private static int gcd(int a, int b) {
        int temp;

        while (b > 0) {
            temp = a;
            a = b;
            b = temp % b;
        }

        return a;
    }

    private static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}

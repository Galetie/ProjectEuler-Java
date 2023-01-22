package Solutions;

public class Problem4 {
    public static int solution() {
        int largestPalindrome = 0;
        int product;

        // 800^2 = 640,000 easily brute forced...
        for (int a = 999; a > 99; a--) {
            if (a * 999 < largestPalindrome) {
                return largestPalindrome; // Break early
            }
            for (int b = 999; b > 99; b--) {
                product = a * b;
                if (isPalindrome(product) && product > largestPalindrome) {
                    largestPalindrome = product;
                }
            }
        }

        // Largest value found
        return largestPalindrome;
    }

    public static boolean isPalindrome(int candidate) {
        int original = candidate;
        int reversed = 0;

        while (candidate > 0) {
            reversed *= 10;
            reversed += candidate % 10;
            candidate /= 10;
        }

        return reversed == original;
    }
}

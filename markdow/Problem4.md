## Problem statement
> A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.  
>
> Find the largest palindrome made from the product of two 3-digit numbers.

A bit of a change from the previous problems, this time we're asked simply to find the largest palindromic number that is the result of the product of two 3 digit numbers **a** and **b**. Firstly, let's make sure our answer can fit inside a standard 32-bit integer value. The largest values for **a** and **b** is 999, the result of the product of which is 998001 which is well within the integer limits. Secondly, before we go trying to make this as efficient as possible, can it simply be brute forced?  

Both **a** and **b** will range from 100 to 999, which is 899 each giving us a total of ~900,000 loops. Let's give it a shot!

## How do we test if a number is palindromic?
A palindromic number, or in reality, a palindromic sequence is palindromic if it is the same backwards to forwards, that is for example, the number **100001** is palindromic because the reverse is also **100001**. There's various ways in which we may determine if these sequences are palindromic.

### The string way
If we have a number **n** which we want to know is palindromic, we could simply convert the number to a string, and then create a copy of the string but in reverse. Finally, we would test for equality. This is done fairly easily in Java using the StringBuilder class.
```java
public static boolean isPalindrome(int candidate) {
    String original = Integer.toString(candidate);
    String reversed = new StringBuilder(original).reverse().toString();
    return original.equals(reversed);
}
```

### The mathematical way
Alternatively, we may use some math to extract the digits and add them in a reversed order to obtain the reversed version.
```java
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
```
So that's probably a bit different right? How does it work? Similarly to what we did in the string method, we maintain a copy of the original. Now the interesting part, reversing the original number. A number, for example 12345 in reverse is 54321. Ideally what we need is a way to extract the digits of the number one by one and append them to the end of our **reversed** variable. Note that 54321 / 10 = 5432 with a remainder of 1. So with that in mind we may simply use the modulo operator to extract the digits.  
  
So here's what we do. 
- We move the entirety of **reversed** one place over by multiplying by 10.
- We extract the last digit using **candidate % 10**
- We divide candidate by 10 to move it all one place to the right.
  - **54321 / 10** becomes **5432** 
- We stop once candidate equals 0, signalling we have consumed all the digits.

## Getting a solution
Ok so we've abstracted away obtaining the answer for whether a number is palindromic, lets for get that part and move on to brute forcing the products we need to test.
```java
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
```
What's happening here? firstly we need to keep track of the largest found palindrome. We also need somewhere to store the product. That's our two initial variables. Next we define our two operands **a** and **b**, we're starting from a and b = 999 in order to enable us to break early if we end up in a position which cannot possibly produce a larger value than the largest found value.  

Speaking of which, Our first step within the **a** loop is to decide if we should break early. The largest possible product for **a** and **b** where **a** is known and **b** is undecided will be **a x 999**. So with that knowledge, if **a x b** is less than the largest found value, we can never possibly produce a larger value, and we may as well exit early! So finally all we must do is check if the product of **a** and **b** is palindromic, and additionally if it is larger than the largest found value. Simple enough!

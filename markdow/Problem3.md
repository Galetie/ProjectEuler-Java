# Table of contents
1. [Problem statement](#problem-statement)
2. [Recap](#recap)
3. [Breaking down the problem](#breaking-down-the-problem)
4. [Solution](#solution)

## Problem statement <a name="problem-statement"></a>
>The prime factors of 13195 are 5, 7, 13 and 29.  
What is the largest prime factor of the number 600851475143?

Alright sounds simple enough, calculate the factors of the number,
if its prime remember it, return the biggest prime we found. Some considerations,
however, must be made. The number 600,851,475,143 is quite large.

In Java, the integer variable type is 4 bytes and ranges from
-2,147,483,648 to 2,147,483,647. Significantly smaller than our target number.
In order to account for this limit, we'll have to use Java's **long**
data type which ranges from -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807.

## Recap <a name="recap"></a>
To solve this problem we'll be using our Prime class
You can find that class [**here**](https://github.com/Galetie/Primes-Java/tree/Part1), and the
corresponding article from the Prime series [**here**](https://jlog.au/Blog/article/63cd1fd52c1e3ac3f07b1841).

## Breaking down the problem <a name="breaking-down-the-problem"></a>
The problem number being quite large means we'll have to spend quite a bit of
processing time checking a lot of numbers, so we need to find ways to cut down
the number of *numbers* we check. Firstly, we only care about primes
so we can cut out checking all the even numbers. Secondly, from the prime series part 1,
we know that we only need to check up to the square root of a
given number when checking for factors. Hopefully this will be enough
to get our result in a timely manner.
- Note that the square root of the problem number is ~= 750000  
This is within the limits of a standard int so we will use an integer to keep track
of the largest prime

## Solution <a name="solution"></a>
```java
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
```
> **Output:** 6857

The execution time is under a second, so no further optimization is required.
Turns out its pretty fast to check ~325,000 numbers.
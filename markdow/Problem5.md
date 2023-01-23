## Table of contents

## Problem statement
> 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.  
>  
> What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?

Like most of Project Euler's problems, there exists 2 ways in which we may ultimately solve this problem. Firstly
we may brute force it. Secondly, we could do some math. Here I'll discuss both approaches.

## Brute force
To start, let's discuss our worst case scenario search space. The absolute bare minimum we would have to search
is **20! = 20 * 19 * 18... = 2,432,902,008,176,640,000**, which luckily does fit within a long data type. Searching a 
space that large will take a very, very long time. probably around the order of 14 or so years in fact. Let us simply
hope then that we may exit early.  

In order to skip the most values at once, we will check only the multiples of 20. This should cut our search space
by 95%. Sounds like a lot but its still 121,645,100,408,832,000 possibilities to check (or about a year).
Let's give it a shot anyway.   

Our algorithm will be simple:  
For each multiple of 20, starting at 20, check if the number is also divisible by each number 19 through 2.
If a given number is indeed divisible by all the numbers 19 through 2, we have found the smallest multiple and
can return.
```java
/*
 * Solution A implements the brute forced non-mathematical approach to find the correct solution
 * We simply check every multiple of 20 in order to avoid checking the most obvious non-answers
 * and then check their divisibility
 */
private static long solutionA() {
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
            return candidate;
        }
    }
}
```
>**output:** 232792560

And indeed the result did allow us to exit early after only checking around 11 million possible results.
Now for the math.

## Math
Let's have a look at their initial proposition.
>2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.

Perhaps, somehow, by some mathematical coincidence or magic, there's a particular reason or underlying pattern for why
this (or indeed any other number) is the lowest possible multiple of all the aforementioned numbers. To some of you,
this may sound like another question: "What is the **Least Common Multiple (LCM)** of the numbers 1 through 10"
So we need to find the LCM of 1 through 10, thankfully that's not too hard.

### GCD and LCM
In order to calculate the LCM, we need to use the formula **(a * b) / gcd(a, b)**, where **gcd** is the
greatest common divisor of **a** and **b**. Seeing as we cannot make an LCM function without a GCD function
let's start there. We will use the [Euclidian Algorithm](https://en.wikipedia.org/wiki/Euclidean_algorithm)
to calculate the GCD of both **a** and **b**. See the implementation [here](https://en.wikipedia.org/wiki/Euclidean_algorithm#Implementations).
It's quite simple and implemented as such:
```java
private static int gcd(int a, int b) {
    int temp;

    while (b > 0) {
        temp = a;
        a = b;
        b = temp % b;
    }

    return a;
}
```
We start by defining a temporary variable in order to remember the value of **a** after we mutate it.
The Euclidean algorithm simply iterates through the remainder of the division between a and b until b
divides evenly, thus finding the greatest common divisor between the two. With this in hand, we can move on to the
LCM function:
```java
private static int lcm(int a, int b) {
    return a * b / gcd(a, b);
}
```
This tiny function uses the aforementioned formula to calculate the lcm for the two given integers.

### Solution
With the lcm function in hand, we can implement an iterative solution to find the lcm for all numbers 1 
through x. To do so I'll first explain how to find the lcm of 3 numbers. Here are the multiples of 3, 5 and 6:

| multiple | 1   | 2   | 3   | 4   | 5        | 6        | 7   | 8   | 9   | 10       |
|----------|-----|-----|-----|-----|----------|----------|-----|-----|-----|----------|
| **3**    | 3   | 6   | 9   | 12  | 15       | 18       | 21  | 24  | 27  | **30**   |
| **5**    | 5   | 10  | 15  | 20  | 25       | **30**   | ..  | ..  | ..  | ..       |
| **6**    | 6   | 12  | 18  | 24  | **30**   | ..       | ..  | ..  | ..  |          |

As highlighted above, 30 is the LCM of 3, 5 and 6. Note that the lcm of 3 and 5 is **15**. Now consider
that the lcm of 15 and 6 is 30. Effectively to calculate the lcm of any set of numbers, we must iteratively
calculate the lcm for each pair of numbers. That is for example, the lcm of **a**, **b** and **c** is:
>***lcm(a, b, c) = lcm( lcm(a, b), c)***

So let's do it:
```java
private static int solutionB(int max) {
    int currentLCM = 1;

    for (int b = 3; b < max; b++) {
        currentLCM = lcm(currentLCM, b);
    }

    return currentLCM;
}
```
Starting with the currentLCM equal to the lcm of 1 and 2 (**1**), we then calculate the next lcm using
**currentLCM** and the next value. Finally, we return the lcm.
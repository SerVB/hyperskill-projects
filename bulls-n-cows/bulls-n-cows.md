# Bulls'n'Cows
In this project you will implement an advanced version of the classic code-breaking game "[Bulls and Cows](https://en.wikipedia.org/wiki/Bulls_and_Cows)".  
The project is designed for beginners.

## What Will You Learn?
- Create a Java console application.
- Work with standard output and input.
- Use integer arithmetic.
- Generate random numbers.
- Store data in data structures.
- Handle errors.

## Stages
### 1. Game log
#### Learn topics
- [Printing data](https://hyperskill.org/learn/lesson/104662/) (and previous topics).
- [Fork and pull request](https://hyperskill.org/learn/lesson/159182/) (and previous topics).

#### Description
Let's start!  
Do you know the numerical version of the game? In brief, there are two players. The first player writes 4-digit secret number with different digits and the second player tries to guess it. At each turn the second player writes 4-digit number &mdash; an answer. Then the first player grades the answer using "bulls and cows" notation. If the digit in the answer matches the digit and the position in the secret number, then it's called "a bull". If the digit is presented in the secret number but the position doesn't match, then it's called "a cow". Finally, the first player says how many bulls and cows there are. The info is general, in other words, it isn't bound to the each digit. For example:

The secret number is 4931.  
The answer is 1234.  
The grade is 1 bull and 2 cows.

Here 3 is a bull; 1 and 4 are cows. If all digits are bulls, the secret is guessed and the game ends. Otherwise the game continues, the second player tries to give an answer again.

You can get more info about the game at [Wikipedia](https://en.wikipedia.org/wiki/Bulls_and_Cows).

In this stage you should submit a program that outputs a game log. The log must contain at least two turns. It can be predefined &mdash; it doesn't matter now.

#### Output example
```
The secret is prepared: ****.

Turn 1. Answer:
1234
Grade: 1 cow.

Turn 2. Answer:
5678
Grade: 1 cow.

Turn 3. Answer:
9012
Grade: 1 bull and 1 cow.

Turn 4. Answer:
9087
Grade: 1 bull and 1 cow.

Turn 5. Answer:
1087
Grade: 1 cow.

Turn 6. Answer:
9205
Grade: 3 bulls.

Turn 7. Answer:
9305
Grade: 4 bulls.
Congrats! The secret number is 9305.
```

#### Output example
```
The secret is prepared: ****.

Turn 1. Answer:
1234
Grade: nothing.

Turn 2. Answer:
9876
Grade: 4 bulls.
Congrats! The secret number is 9876.
```

#### Let’s go!
You can start with the following code. The code prints the first line of the game log. So you can just add printing of other lines.

```java
public final class BullsAndCowsGame {
    public static void main(final String[] args) {
        System.out.println("The secret is prepared: ****.");
    }
}
```

### 2. Grader
#### Learn topics
[Conditional statement](https://hyperskill.org/learn/lesson/51347/) and previous topics.

#### Description
In this stage you should write the vital part of the game &mdash; the grader. Use predefined 4-digit number and grade the inputted number (you can do it digit by digit).

#### Input example
```
1234
```

#### Output example
```
Grade: 1 cow(s). The secret number is 9305.
```

#### Input example
```
9087
```

#### Output example
```
Grade: 1 bull(s) and 1 cow(s). The secret number is 9305.
```

### 3. Pseudo random secret number
#### Learn topics
- [Naming variables](https://hyperskill.org/learn/lesson/62778/).
- [Branching statements](https://hyperskill.org/learn/lesson/51351/) (and previous topics).

#### Description
Implement pseudo random secret number generation by the given length. If the given length is greater than 10, print a message and don't generate the number.

We suggest you using the following algorithm of generation.

```java
public final class SecretGenerator {
    public static void main(final String[] args) {
        long pseudoRandomNumber = System.nanoTime();
    }
}
```

This code saves nanoseconds since some fixed time to the `pseudoRandomNumber` variable. Now we can assume that this is a random number. So you can generate a secret number by iterating the `pseudoRandomNumber` in the reversed order and add unique digits in the secret number. If the `pseudoRandomNumber` lacks required amount of unique digits, call `System.nanoTime()` again and try to generate the secret number again until you get the code.

#### Input example
```
5
```

#### Output example
```
The random secret number is 48379.
```

#### Input example
```
5
```

#### Output example
```
The random secret number is 52136.
```

#### Input example
```
100
```

#### Output example
```
Can't generate a secret number with a length of 100 because there aren't so many unique digits.
Please input a number not greater than 10. 
```

### 4. Almost classic game
#### Learn topics
[Processing strings](https://hyperskill.org/learn/lesson/59868/) and previous topics.

#### Description
In this stage you should combine the previous parts to the one. At first, ask the player to input the secret number length. Then ask for answers and grade them. Stop the game when the secret number is guessed.

To sum up, implement a playable version of the game.

### 5. Different random numbers generation methods
#### Learn topics
- [Math library](https://hyperskill.org/learn/lesson/51342/) (and previous topics).
- [Random](https://hyperskill.org/learn/lesson/187778/) (and previous topics).

#### Description
Actually, the suggested algorithm of secret code generation was a reinvented bicycle.

Research common pseudo random generation methods such as `Math.random()` and `Random` class.

Briefly, you can generate next digits by the following code:

```java
import java.util.Random;

public final class RandomTest {
    public static void main(final String[] args) {
        final int nextDigit1 = (int) (Math.random() * 10);  // Number in [0; 9]
        final Random random = new Random();
        final int nextDigit2 = random.nextInt(10);  // Number in [0; 9]
        
        System.out.println("Math.random(): " + nextDigit1);
        System.out.println("Random class: " + nextDigit2);
    }
}
```

So choose the method and rewrite secret generation using it. 

### 6. More complicated secret code
#### Learn topics
[Map](https://hyperskill.org/learn/lesson/53858/) and previous topics.

#### Description
What about making secret code harder to guess? Add support for more than 10 symbols: add letters.

You can use `Set<Character>` to store all symbols of the code. So you are able to check fast if the symbol is presented in the code (= if it is a cow).

#### Game start example
```
Input the length of the secret code:
6
Input the number of possible symbols in the code:
16
The secret is prepared: ****** (0-9, a-f).
```

### 7. Handle errors
#### Learn topics
[Exception handling](https://hyperskill.org/learn/lesson/57387/) and previous topics.

#### Description
What if someone enters an answer of wrong length?

And if the number of possible symbols is less then the length of the secret code?

Answer contains wrong symbols?

Inputted strings aren't numbers?

Let's handle errors like this. For now you are able to implement this without a `try catch` construction. Use the rule: if you can avoid exception-based logic, avoid it! 

So implement error messages when the input is wrong to protect your program.

#### Game start example
```
Input the length of the secret code:
6
Input the number of possible symbols in the code:
5
It's not possible to generate a code with a length of 6 having 5 unique symbols.
```

#### Game start example
```
Input the length of the secret code:
abc 0 -7
"abc 0 -7" isn't a valid number.
```

#### Game start example
```
Input the length of the secret code:
6
Input the number of possible symbols in the code:
37
Maximum number of possible symbols in the code is 36 (0-9, a-z).
```

# Numeral system converter
In this project, you will develop an application that converts numbers into different numeral systems.  
The project is designed for beginners.

## What Will You Learn?
- Create a Java console application.
- Work with standard output and input.
- Use standard Java library.
- Study some math algorithms.

## Stages
### 1. Single conversion
#### Learn topics
[Printing data](https://hyperskill.org/learn/lesson/104662).

#### Description
If you don't know what's a numeral system, take a look at [Wikipedia](https://en.wikipedia.org/wiki/Numeral_system).

Implement a program that outputs two numbers: the first number is decimal and the second one is binary. The numbers must be equal. You can choose a number you like.

#### How to submit?
This stage is auto-graded. The grader will check that:
* you output a single line,
* there are two numbers,
* the first number is decimal,
* the second number is binary (it starts with `0b` and contains only `0` and `1`),
* the first number is equal to the second one.

You can start with the following code. The code prints a line `Change me`. So you can just change the output to a proper one.

```java
final class NumeralSystemConverter {
    public static void main(final String[] args) {
        System.out.println("Change me");
    }
}
```

#### Output example
```
10 = 0b1010
```

#### Output example
```
2 is equal to 0b10
```

### 2. The last digit of the octal number
#### Learn topics
[Characters](https://hyperskill.org/learn/lesson/51344).

#### Description
What's the last digit of the given number in [base](https://en.wikipedia.org/wiki/Radix) 8? Implement a program that answers to this question!

#### How to submit?
This stage is auto-graded. The grader will **input a number** in base 10 and then check that the **last line of your output** is equal to the right answer.

You can start with the following code. The code reads the number. So you can just add an output of the answer.

```java
import java.util.*;

final class NumeralSystemConverter {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        
        final int number = scanner.nextInt();
    }
}
```

#### Console examples
Lines after `>` are inputted by a user.
```
Number: >11
3
```

```
Number: >42
2
```

### 3. Convert decimals
#### Learn topics
[Switch statement](https://hyperskill.org/learn/lesson/51348).

#### Description
Now let's implement a simple converter. It will convert the given decimal number to the given [radix](https://en.wikipedia.org/wiki/Radix). You should support three radices with prefixes:
* binary (`0b`),
* octal (`0`),
* hexadecimal (`0x`).

To get a string with answer, use `Long.toString(sourceNumber, destinationRadix)` expression.

#### How to submit?
This stage is auto-graded. The grader will **input two lines** (a number and a radix) and then check that the **last line of your output** is the correct number representation in the given radix (don't forget about the prefix).

#### Console examples
Lines after `>` are inputted by a user.
```
Source number: >11
Target radix: >2
0b1011
```

```
Source number: >8
Target radix: >8
010
```

```
Source number: >0
Target radix: >16
0x0
```

### 4. Any radix converter
#### Learn topics
[Iterating over arrays](https://hyperskill.org/learn/lesson/59867).

#### Description
Add support for reading the source number in the given radix and conversion it to another given radix.

As there are 26 Latin letters, the maximum radix is 10+26=36. The minimum radix is 1: the number contains a symbol `1` [number] times.

To input radix from 2 to 36 use `Long.parseLong(sourseNumberString, sourceRadix)` expression.

To input and output radix 1 use a loop.

#### How to submit?
This stage is auto-graded. The grader will **input three lines**: the source radix, the source number, and the target radix. Then it will check that the **last line of your output** is the correct number representation in the given radix (don't use prefixes now).

#### Console examples
Lines after `>` are inputted by a user.
```
Source radix: >10
Source number: >11
Target radix: >2
1011
```

```
Source radix: >1
Source number: >11111
Target radix: >10
5
```

```
Source radix: >10
Source number: >1000
Target radix: >36
rs
```

```
Source radix: >21
Source number: >4242
Target radix: >6
451552
```

### 5. Fractional numbers
#### Learn topics
[Processing strings](https://hyperskill.org/learn/lesson/59868), [StringBuilder](https://hyperskill.org/learn/lesson/118659), [Programs with doubles](https://hyperskill.org/learn/lesson/101660).

#### Description
Add support for fractional numbers. If there is a decimal point, use an algorithm to get the fractional answer.

Round your answer to 5 fractional symbols to zero.

If the target radix is 1, don't compute the fractional part. 

#### Algorithm
You can use the following algorithm.

Interpret the source number as a string $\overline{int.frac}_{base}$ and parse integer and fraction independently.

Save the integer part as a `long` using the standard library.

Save the fractional part as a `double` using formula like $\frac{f}{{base}^1} + \frac{r}{{base}^2} + \frac{a}{{base}^3} + \frac{c}{{base}^4}$.

Of course, you should support numbers of bigger length.

To compute symbols of the target number fractional part, multiply it to $targetRadix$ and get fraction symbol-by-symbol. Here is an example:

$0.234_{10}$ to base 7.

1. $0.234 \cdot 7 = 1.638$: the first fraction symbol is 1.
1. Remove integer part. $0.638 \cdot 7 = 4.466$: the second fraction symbol is 4.
1. Remove integer part. $0.466 \cdot 7 = 3.262$: the third fraction symbol is 3.
1. The same for the next symbols.

#### How to submit?
This stage is auto-graded. The grader will **input three lines**: the source radix, the source number, and the target radix. Then it will check that the **last line of your output** is the correct number representation in the given radix (don't use prefixes now).

#### Console examples
Lines with `>` are inputted by a user.
```
Source radix: >10
Source number: >0.234
Target radix: >7
0.1431556030
```

```
Source radix: >10
Source number: >10.234
Target radix: >7
13.1431556030
```

```
Source radix: >6
Source number: >2.555
Target radix: >1
11
```

```
Source radix: >35
Source number: >af.xy
Target radix: >17
148.g88a8a09b7
```

```
Source radix: >10
Source number: >11
Target radix: >2
1011
```

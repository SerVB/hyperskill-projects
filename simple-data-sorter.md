# Simple Data Sorter
In this project you will develop a Java program that sorts data.  
The project is designed for beginners.

## Motivation
You need to know the result of elections or find the most used word in the text? You will be able to use your program to do that for you!

## What Will You Learn?
- Create a Java console application.
- Parse run arguments to set the application behavior.
- Use default and custom sorting methods.
- Read data from console and from file.
- Write data to file console and to file.
- Handle errors.

## How to submit?
Fork the repo TODO LINK. Send a pull request after each stage.

## Stages
### 1. The greatest number
#### Learn topics
- [Branching statements](https://hyperskill.org/learn/lesson/51351/) (and previous topics).
- [Fork and pull request](https://hyperskill.org/learn/lesson/159182/) (and previous topics).

#### Description
Read integers from the console. Please note that there can be not only a single integer in a line: if there are several numbers in the line, they are divided by spaces.  
Your program must process all the input lines. To end the input, `Ctrl+D` is sent.

Then print a line like `Total numbers: X`.  
Then print a line like `The greatest number: Y (Z times)`.

#### Input example
```
1 -2   33 4
42
1                 1
```

#### Output example
```
Total numbers: 7.
The greatest number: 42 (1 time).
```

#### Let’s go!
You can start with the following code. The code performs the reading task: it saves every integer from the console to the `number` variable. So you can just add count calculation and printing.

```java
import java.util.*;

public final class SimpleDataSorter {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLong()) {
            final long number = scanner.nextLong();
        }
    }
}
```

### 2. The greatest data 
#### Learn topics
- [Command-line arguments](https://hyperskill.org/learn/lesson/62780/) (and previous topics).
- [Programs with doubles](https://hyperskill.org/learn/lesson/101660/) (and previous topics).
- [Processing strings](https://hyperskill.org/learn/lesson/59868/) (and previous topics).

#### Description
Parse the "data type" run argument. After the argument name there should be the "data type name" argument. Give names to the arguments. Consider using static final strings to store the given names.

Implement searching for line and word with the greatest length.

If the "data type" isn't presented, assume that the data type is word.

Add output of the greatest data percentage.

#### Run configuration examples
```
java SimpleDataSorter -dataType long 
```
```
java SimpleDataSorter -dataType line 
```
```
java SimpleDataSorter -dataType word 
```

#### Input example
```
1 -2   33 4
42
1                 1
```

#### Output example for words
```
Total words: 7.
The longest word: 33 (1 time, 14%).
```

#### Output example for lines
```
Total lines: 3.
The longest line:
1                 1
(1 time, 33%).
```

### 3. Sort some integers
#### Learn topics
- [ArrayList](https://hyperskill.org/learn/lesson/88500/) (and previous topics).
- [Merge sort](https://hyperskill.org/learn/lesson/66716/) (and previous topics).

#### Description
Parse the "sort integers" argument. 

Print a line like `Sorted data:` and after it print all the read integers sorted in natural order (use merge sort). You can either print all the numbers in a single line or print every number in its own line, it doesn't matter now.

If there is a "sort integers" argument, ignore other arguments.

#### Run configuration examples
```
java SimpleDataSorter -sortIntegers
```

#### Input example
```
1 -2   33 4
42
1                 1
```

#### Output example (for sorting task)
```
Total numbers: 7.
Sorted data: -2 1 1 1 4 33 42
```

### 4. Count of each data entry
#### Learn topics
[Map](https://hyperskill.org/learn/lesson/63074/) and previous topics.

#### Description
Implement sorting by count: in the result there should be pairs like `(count, dataEntry)` sorted by count.

Remove parsing of the "sort integers" argument.

Parse the "sorting type" run argument. After the argument name there should be the "sorting name" argument. Assume the default sorting type is simple natural order.

If "data type" is word or line, use `String.compareTo(String)` method to compare strings in sorting.

To sum up, your program should support two sorting methods now and work with all types of data.

#### How-to
You can use the following algorithm:
1. Sort data entries into `List<DataType> sortedDataEntries`.
1. Iterate over it and create `Map<DataType, Integer> dataEntryToCount`.
1. Iterate over it and create `List<Integer> counts`.
1. Sort it into `List<Integer> sortedCounts` and remove duplicates in it.
1. Iterate over `dataEntryToCount` and create `Map<Integer, Set<DataEntry>> countToDataEntries`.
1. Now iterate over `sortedCounts` and print all data entries from `countToDataEntries.get(count)`.

Or you can use [sorting with comparator](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#sort-java.util.List-java.util.Comparator-) to skip last four actions.

#### Run configuration examples
```
java SimpleDataSorter -sortingType natural -dataType long
```
```
java SimpleDataSorter -dataType word -sortingType byCount 
```

#### Input example
```
1 -2   33 4
42
1                 1
```

#### Output example for sorting longs by count
```
Total numbers: 7.
-2: 1 time, 14%
4: 1 time, 14%
33: 1 time, 14%
42: 1 time, 14%
1: 3 times, 43%
```

### 5. Watch out! Errors
#### Learn topics
[Exception handling](https://hyperskill.org/learn/lesson/57387/) and previous topics.

#### Description
What if someone runs your program like this `java SimpleDataSorter -sortingType`? The value of the "sorting type" argument is missing!

And if "data type" is long but there are words in the input text?

Unknown arguments?

Let's handle errors like this. For now you are able to implement this without a `try catch` construction. Use the rule: if you can avoid exception-based logic, avoid it! 

So implement error messages when the input is wrong.

#### Run configuration example
```
java SimpleDataSorter -sortingType
```

#### Output example
```
No sorting type defined!
```

#### Run configuration example
```
java SimpleDataSorter -dataType long -sortingType natural -abc
```

#### Input example
```
a 2 -42
```

#### Output example
```
"-abc" isn't a valid parameter. It's skipped.
"a" isn't a long. It's skipped.
Total numbers: 7.
Sorted data: -42 2
```

### 6. Work with files
#### Learn topics
- [Reading files](https://hyperskill.org/learn/lesson/90824/) (and previous topics).
- [Writing files](https://hyperskill.org/learn/lesson/90826/) (and previous topics).

#### Description
Sometimes it's useful to read data from file and write the result to another file.

Implement parsing of two more arguments: "input file" and "output file".  
If the "input file" argument is presented, read input data from the file with the given name.  
If the "output file" argument is presented, print only info messages to console. Print the result to the file.

Use `try catch` constructions to handle exceptions. 

#### Run configuration examples
```
java SimpleDataSorter -sortingType byCount -inputFile input.txt
```

#### Run configuration examples
```
java SimpleDataSorter -sortingType byCount -inputFile data.dat -outputFile out.txt
```

### 7. The end?
#### Description
Now you can implement any feature you want!

We give you some ideas here:
- reversed order sorting;
- case insensitive word sorting by count;
- output format (e. g. data entry per a line or all in one line);
- and even maximum size of RAM allowed to use (if the file exceeds the RAM size, use temporary files to store intermediate results).

Good luck in the programming world!

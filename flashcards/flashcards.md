# Flashcards

In this project you will implement a program helping to memorize data. The program will emulate a set of [flashcards](https://en.wikipedia.org/wiki/Flashcard).  
The project is designed for beginners.

## What Will You Learn?
- Create a Java console application.
- Work with standard output and input.
- Generate random numbers.
- Store data in data structures.
- Work with files.
- Handle errors.
- Parse run arguments to set the application behavior.

## Stages
### 1. A card
#### Learn topics
[Printing data](https://hyperskill.org/learn/lesson/104662/).

#### Description
Implement a program that outputs a card and its definition. You can print a card and a definition you like.

#### How to submit?
This stage is auto-graded. The grader will check that:
* you output 4 lines,
* the first line is `Card:`,
* the third line is `Definition:`.

You can start with the following code. The code prints the first line. So you can just add printing of other lines.

```java
final class Flashcards {
    public static void main(final String[] args) {
        System.out.println("Card:");
    }
}
```

#### Output example
```
Card:
purchase
Definition:
buy
```

#### Output example
```
Card:
cos'(x)
Definition:
-sin(x)
```

### 2. Create a card
#### Learn topics
[Conditional statement](https://hyperskill.org/learn/lesson/51347/), [String](https://hyperskill.org/learn/lesson/51353/).

#### Description
Implement a custom card creation mechanism.

#### How to submit?
This stage is auto-graded. The grader will input 3 lines (card, definition, answer) and check that your output contains a word:
* `wrong` if the answer isn't equal to the definition,
* `right` if the answer is equal to the definition.

You can start with the following code. The code reads the first line: the card. Add reading of other lines and verification.

```java
import java.util.*;

final class Flashcards {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        
        final String card = scanner.nextLine();
    }
}
```

#### Input example
```
a purring animal
cat
cat
```

#### Output example
```
Your answer is right!
```

#### Input example
```
a barking animal
dog
cat
```

#### Output example
```
Your answer is wrong...
```

### 3. Create a set of cards
#### Learn topics
[Branching statements](https://hyperskill.org/learn/lesson/51351/), [Iterating over arrays](https://hyperskill.org/learn/lesson/59867/), [Fork and pull request](https://hyperskill.org/learn/lesson/159182/).

#### Description
Implement a working application now! Add support for addition of multiply cards. Then ask user to answer all the definitions.

You can follow the algorithm. Firstly, ask the user to input the desired number of cards. Then create two string arrays (`String[]`): `cards` and `definitions`. Process all inputted cards and save the i-th card to `cards[i]` and `definitions[i]`. Finally iterate over saved cards and ask the user to answer.

#### Console example
```
Input the number of cards:
2
The card #1:
black
The definition of the card #1:
white
The card #2:
white
The definition of the card #2:
black
Print the definition of "black":
white
Correct answer. Print the definition of "white":
blue
Wrong answer (the correct one is "black").
```
Here every second line is inputted by the user.

#### How to submit?
Fork the repo TBD. Starting from this stage, you should send a pull request after a stage. 

### 4. Answers analytics
#### Learn topics
[Exception handling](https://hyperskill.org/learn/lesson/57387/), [Map](https://hyperskill.org/learn/lesson/53858/).

#### Description
There is a common situation that the answer is wrong for the given card but it's correct for another card. Let's notice the situations like this.

Remove array-based storage. Use two maps (`Map<String, String>`): `cardToDefinition` and `definitionToCard`. So if the definition is wrong but it is presented in `definitionToCard`, output the original card.

When the user tries to add a duplicated card or a definition, forbid it. For now you are able to implement this without a `try catch` construction. Use the rule: if you can avoid exception-based logic, avoid it!

#### Console example
```
Input the number of cards:
2
The card #1:
a brother of one's parent
The definition of the card #1:
uncle
The card #2:
a part of the body where the foot and the leg meet
The definition of the card #2:
ankle
Print the definition of "a brother of one's parent":
ankle
Wrong answer (the correct one is "uncle", you've just written a definition of "a part of the body where the foot and the leg meet" card). Print the definition of "a part of the body where the foot and the leg meet":
???
Wrong answer (the correct one is "ankle").
```
Here every second line is inputted by the user.

### 5. Serialization/Deserialization
#### Learn topics
[Reading files](https://hyperskill.org/learn/lesson/90824/), [Writing files](https://hyperskill.org/learn/lesson/90826/), [Random](https://hyperskill.org/learn/lesson/187778/).

#### Description
Improve the application interactivity. Ask the user for an action and do it.

Support actions:
* add a card: `add`,
* remove a card: `remove`,
* load cards from file ("deserialization"): `import`,
* save cards to file ("serialization"): `export`,
* ask for definition of some random cards: `ask`,
* exit the program: `exit`.

You can use the following file format. The file consists of pairs of lines. The first line of each pair is a card, the second line is a definition of the card.

#### Console example
```
Input the action (add, remove, import, export, ask, exit):
add
The card:
Great Britain
The definition of the card:
London
The pair ("Great Britain":"London") is added.

Input the action (add, remove, import, export, ask, exit):
remove
The card:
Wakanda
Can't remove "Wakanda": there is no such card.

Input the action (add, remove, import, export, ask, exit):
import
File name:
capitals.txt
28 cards have been loaded.

Input the action (add, remove, import, export, ask, exit):
ask
How many times to ask?
1
Print the definition of "Russia":
Moscow
Correct answer.

Input the action (add, remove, import, export, ask, exit):
export
File name:
capitalsNew.txt
29 cards have been saved.

Input the action (add, remove, import, export, ask, exit):
exit
Bye bye!
```

### 6. Statistics
#### Learn topics
[ArrayList](https://hyperskill.org/learn/lesson/88500/).

#### Description
Add some statistics features. We offer you to implement the following:
* Action `log` saves the application log to the given file. For example, the program uses `ArrayList` to store all lines have been inputted or outputted in the console and then saves them to the file.
* Action `hardest card` prints the card that has the most mistakes. You can store the mistake count in `Map<String, Integer>`. Also you should implement `reset stats` action (it erases mistakes count for all cards) and update serialization/deserialization to store sets of three lines (card, definition, mistakes) instead of pairs (card, definition).

### 7. Settings
#### Learn topics
[Command-line arguments](https://hyperskill.org/learn/lesson/62780/).

#### Description
Surely the user often use files to save progress and restore it at the next program run. It's tedious to print the actions manually. Sometimes you can just forget to do it! So let's add run arguments that defines which file to read at start and which file to save at exit.

#### Run arguments examples
```
java Flashcards -import derivatives.txt
```
```
java Flashcards -export animals.txt
```
```
java Flashcards -import words13june.txt -export words14june.txt
```
```
java Flashcards -import vocab.txt -export vocab.txt
```

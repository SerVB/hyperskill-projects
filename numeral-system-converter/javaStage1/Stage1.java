import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Clue {
    // you can store here any variables you need for test

    // for example - feedback
    String feedback;

    Clue(String feedback) {
        this.feedback = feedback;
    }
}


// Class for storing one test
class Test {
    String input;
    Clue clue;

    // arguments passed to main method
    ArrayList<String> args = new ArrayList<>();

    // files needed to be set up before test
    HashMap<String, String> files = new HashMap<>();

    Test(String input) {
        this(input, null);
    }

    Test(String input, Clue clue) {
        this.input = input;
        this.clue = clue;
    }

    Test addArgument(String arg) {
        args.add(arg);
        return this;
    }

    Test addFile(String filename, String content) {
        files.put(filename, content);
        return this;
    }
}

// Class for storing result of checking
class CheckResult {
    boolean result;
    String feedback; // feedback that user can see why the test is failed

    CheckResult(boolean result) {
        this(result, "");
    }

    CheckResult(boolean result, String feedback) {
        this.result = result;
        this.feedback = feedback;
    }
}

// you need to write methods generate and check
class SampleTest {

    // you allowed to store information between tests and use it
    static String variableAccessibleBetweenTests = "";

    static final String BINARY_PREFIX = "0b";


    public static Test[] generate() {
        return new Test[]{
                new Test("")
        };
    }

    public static CheckResult check(String reply, Clue clue) {
        // check the solution here
        // reply - all user printed using System.out

        final String[] lines = reply
                .lines()
                .filter(line -> !line.isEmpty())
                .toArray(String[]::new);

        if (lines.length != 1) {
            return new CheckResult(
                    false,
                    String.format(
                            "Your program doesn't print exactly one line. A number of lines found: %d.",
                            lines.length
                    )
            );
        }

        final Set<String> words = Arrays
                .stream(lines[0].split(" "))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toSet());

        final String[] binaries = words
                .stream()
                .filter(word -> word.startsWith(BINARY_PREFIX))
                .map(word -> word.split(BINARY_PREFIX)[1])
                .filter(word -> word.chars().mapToObj(i -> (char) i).allMatch(c -> c == '1' || c == '0'))
                .toArray(String[]::new);

        if (binaries.length != 1) {
            return new CheckResult(
                    false,
                    String.format(
                            "Your output doesn't contain exactly one binary. Binaries have been found: %s.",
                            Arrays.toString(binaries)
                    )
            );
        }

        final String[] numbers = words
                .stream()
                .filter(word -> word.chars().mapToObj(i -> (char) i).allMatch(Character::isDigit))
                .toArray(String[]::new);

        if (numbers.length != 1) {
            return new CheckResult(
                    false,
                    String.format(
                            "Your output doesn't contain exactly one number. Numbers have been found: %s.",
                            Arrays.toString(binaries)
                    )
            );
        }


        final BigInteger binary = new BigInteger(binaries[0], 2);
        final BigInteger number = new BigInteger(numbers[0], 10);

        if (!binary.equals(number)) {
            return new CheckResult(
                    false,
                    String.format("%s%s != %s", BINARY_PREFIX, binaries[0], numbers[0])
            );
        }

        // means the user passed this test
        return new CheckResult(true);
    }

    private static final String currDir =
            System.getProperty("user.dir") + File.separator;

    public static void createFiles(Map<String, String> files) {
        files.forEach((filename, content) -> {
            try {
                Files.write(Paths.get(currDir + filename), content.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void deleteFiles(Map<String, String> files) {
        files.forEach((filename, content) -> {
            try {
                Files.deleteIfExists(Paths.get(currDir + filename));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    // returns user output
    public static String run(Test test) {
        OUT.reset();
        System.setIn(new ByteArrayInputStream(test.input.getBytes()));

        createFiles(test.files);

        Solution.main(test.args.toArray(new String[0]));

        deleteFiles(test.files);

        String reply = OUT.toString();

        // avoid different line endings - always "\n"
        reply = reply
                .replace("\r\n", "\n")
                .replace("\r", "\n");

        return reply;
    }

    private static final ByteArrayOutputStream OUT = new ByteArrayOutputStream();

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // So numbers always 12.05, not 12,05
        final PrintStream stdout = System.out;
        System.setOut(new PrintStream(OUT)); // change System.out

        final Test[] tests = generate();

        int currTest = 0;

        try {
            for (final Test test : tests) {
                ++currTest;
                final String output = run(test);
                final CheckResult result = check(output, test.clue);

                if (!result.result) {
                    final String errorText = "Wrong answer on test " + currTest;
                    stdout.println(errorText + "\n" + result.feedback);
                    return;
                }
            }
        } catch (final Exception ex) {
            final String errorText = "Exception in test " + currTest;
            stdout.println(errorText);
            ex.printStackTrace(stdout);
            return;
        }

        stdout.println("Good job! :)");
    }
}

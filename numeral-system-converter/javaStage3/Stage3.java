import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Clue {
    // you can store here any variables you need for test

    final String answer;

    Clue(String answer) {
        this.answer = answer;
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

    static String prefix(final int base) {
        if (base == 2) {
            return "0b";
        } else if (base == 8) {
            return "0";
        } else {
            return "0x";
        }
    }

    public static Test[] generate() {
        final List<Integer> tests = new ArrayList<>();
        tests.add(11);
        tests.add(8);
        tests.add(0);

        for (int i = 101; i <= 104; ++i) {
            tests.add(i);
        }

        return tests
                .stream()
                .flatMap(i -> Arrays
                        .stream(new int[]{16, 8, 2})
                        .mapToObj(base -> new Test(
                                "" + i + "\n" + base + "\n",
                                new Clue(prefix(base) + Integer.toString(i, base))
                        ))
                )
                .toArray(Test[]::new);
    }

    public static CheckResult check(String reply, Clue clue) {
        // check the solution here
        // reply - all user printed using System.out

        final String[] lines = reply
                .lines()
                .filter(line -> !line.isEmpty())
                .toArray(String[]::new);

        if (lines.length == 0) {
            return new CheckResult(
                    false,
                    "Your program doesn't print any line."
            );
        }

        final String answer = lines[lines.length - 1];

        if (!answer.equals(clue.answer)) {
            return new CheckResult(
                    false,
                    String.format("Your answer is wrong (%s).", answer)  // TODO: Is it OK to print user's answer? He/She can be a cheater and print input.
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

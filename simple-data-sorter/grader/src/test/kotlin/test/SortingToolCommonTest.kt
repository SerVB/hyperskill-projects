package test

import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import java.util.*
import kotlin.math.roundToInt

fun revealRawTest(clue: SortingToolClue, reply: String): String {
    return with(clue) { "Args:\n${args.joinToString(" ")}\nInput:\n$consoleInput\nYour output:\n$reply\n\n" }
}

class SortingToolClue(val consoleInput: String, val revealTest: Boolean, val args: List<String>)

fun createTest(
    consoleInput: String,
    revealTest: Boolean,
    vararg args: String = arrayOf("-dataType", "long")
): TestCase<SortingToolClue> {
    return TestCase(
        SortingToolClue(consoleInput, revealTest, args.toList()),
        consoleInput,
        args
    )
}

fun checkForLong(clue: SortingToolClue, reply: String): CheckResult {
    val regex = """(\d+)\D+(\d+)\D+(\d+)""".toRegex()
    val matchResult = regex.find(reply)
    if (matchResult == null) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't parse your output. Please check if your output contains three numbers\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't parse your output.")
        }
    }

    val (totalNumbers, greatestNumber, greatestNumberCount) = matchResult.groupValues.drop(1).map { it.toInt() }

    val scanner = Scanner(clue.consoleInput)

    val actualNumbers = mutableListOf<Int>()

    while (scanner.hasNextInt()) {
        actualNumbers.add(scanner.nextInt())
    }

    val actualTotal = actualNumbers.size

    if (actualTotal != totalNumbers) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Total numbers ($totalNumbers) are incorrect. Expected: $actualTotal.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Total numbers are incorrect.")
        }
    }

    val actualMax = actualNumbers.max()

    if (actualMax != greatestNumber) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Greatest number ($greatestNumber) is incorrect. Expected: $actualMax.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Greatest number is incorrect.")
        }
    }

    val actualMaxCount = actualNumbers.count { it == actualMax }

    if (actualMaxCount != greatestNumberCount) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Greatest number times ($greatestNumberCount) are incorrect. Expected: $actualMaxCount.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Greatest number times are incorrect.")
        }
    }

    return CheckResult(true)
}

fun checkForWord(clue: SortingToolClue, reply: String): CheckResult {
    val regex = """(\d+)\D+: (.+) \(\D*(\d+)\D+(\d+)\D*\)""".toRegex()
    val matchResult = regex.find(reply)
    if (matchResult == null) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't parse your output.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't parse your output.")
        }
    }

    val totalWords = matchResult.groupValues[1].toInt()
    val word = matchResult.groupValues[2]
    val longestWordCount = matchResult.groupValues[3].toInt()
    val longestWordPercentage = matchResult.groupValues[4].toInt()

    val scanner = Scanner(clue.consoleInput)

    val actualWords = mutableListOf<String>()

    while (scanner.hasNext()) {
        actualWords.add(scanner.next())
    }

    val actualTotal = actualWords.size

    if (actualTotal != totalWords) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Total words ($totalWords) are incorrect. Expected: $actualTotal.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Total words are incorrect.")
        }
    }

    val actualMax = actualWords.maxBy { it.length }

    if (actualMax != word) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Longest word ($word) is incorrect. Expected: $actualMax.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Longest word is incorrect.")
        }
    }

    val actualMaxCount = actualWords.count { it == actualMax }

    if (actualMaxCount != longestWordCount) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Longest word times ($longestWordCount) are incorrect. Expected: $actualMaxCount.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Longest word times are incorrect.")
        }
    }

    val actualPercentage = (actualMaxCount * 100.0 / actualTotal).roundToInt()

    if (actualPercentage != longestWordPercentage) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Percentage ($longestWordPercentage) is incorrect. Expected: $actualPercentage.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Percentage is incorrect.")
        }
    }

    return CheckResult(true)
}

fun checkForLine(clue: SortingToolClue, reply: String): CheckResult {
    val lines = reply.lines()

    if (lines.size != 4) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't parse your output: expected 4 lines.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't parse your output: expected 4 lines.")
        }
    }

    val totalRegex = """(\d+)""".toRegex()
    val totalMatchResult = totalRegex.find(lines[0])
    if (totalMatchResult == null) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't find number in the first line of your output.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't find number in the first line of your output.")
        }
    }

    val totalLines = totalMatchResult.groupValues[1].toInt()
    val line = lines[2]

    val countRegex = """(\d+)\D+(\d+)""".toRegex()
    val countMatchResult = countRegex.find(lines[3])
    if (countMatchResult == null) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't find two numbers in the last line of your output.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't find two numbers in the last line of your output.")
        }
    }

    val longestWordCount = countMatchResult.groupValues[1].toInt()
    val longestWordPercentage = countMatchResult.groupValues[2].toInt()

    val actualLines = clue.consoleInput.lines()

    val actualTotal = actualLines.size

    if (actualTotal != totalLines) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Total lines ($totalLines) are incorrect. Expected: $actualTotal.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Total lines are incorrect.")
        }
    }

    val actualMax = actualLines.maxBy { it.length }

    if (actualMax != line) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Longest line ($line) is incorrect. Expected: $actualMax.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Longest line is incorrect.")
        }
    }

    val actualMaxCount = actualLines.count { it == actualMax }

    if (actualMaxCount != longestWordCount) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Longest line times ($longestWordCount) are incorrect. Expected: $actualMaxCount.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Longest line times are incorrect.")
        }
    }

    val actualPercentage = (actualMaxCount * 100.0 / actualTotal).roundToInt()

    if (actualPercentage != longestWordPercentage) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Percentage ($longestWordPercentage) is incorrect. Expected: $actualPercentage.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Percentage is incorrect.")
        }
    }

    return CheckResult(true)
}

fun parseLongTokens(input: String): List<Int> {
    val scanner = Scanner(input)

    val longTokens = mutableListOf<Int>()

    while (scanner.hasNextInt()) {
        longTokens.add(scanner.nextInt())
    }

    return longTokens
}

fun parseWordTokens(input: String): List<String> {
    val scanner = Scanner(input)

    val wordTokens = mutableListOf<String>()

    while (scanner.hasNext()) {
        wordTokens.add(scanner.next())
    }

    return wordTokens
}

fun parseLineTokens(input: String): List<String> {
    return input.lines()
}

fun <TokenType : Comparable<TokenType>> checkNatural(
    actualTokens: List<TokenType>,
    sortedTokensParser: (String) -> List<TokenType>,
    clue: SortingToolClue,
    reply: String
): CheckResult {
    val lines = reply.lines()

    if (lines.size != 2) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't parse your output: expected 2 lines.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't parse your output: expected 2 lines.")
        }
    }

    val totalRegex = """(\d+)""".toRegex()
    val totalMatchResult = totalRegex.find(lines[0])
    if (totalMatchResult == null) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't find number in the first line of your output.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't find number in the first line of your output.")
        }
    }

    val totalTokens = totalMatchResult.groupValues[1].toInt()

    val actualTotal = actualTokens.size

    if (actualTotal != totalTokens) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Total tokens ($totalTokens) are incorrect. Expected: $actualTotal.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Total tokens are incorrect.")
        }
    }

    val sortedActualTokens = actualTokens.sorted()

    val sortedTokens = sortedTokensParser(lines[1].substringAfter(":").dropWhile { it in setOf('\n', '\r') })

    val total = sortedTokens.size

    if (actualTotal != total) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Total sorted tokens ($total) are incorrect. Expected: $actualTotal.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Total sorted tokens are incorrect.")
        }
    }

    if (sortedActualTokens != sortedTokens) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Sorted tokens are incorrect.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Sorted tokens are incorrect.")
        }
    }

    return CheckResult(true)
}

fun <TokenType : Comparable<TokenType>> checkByCount(
    actualTokens: List<TokenType>,
    tokenParser: (String) -> TokenType,
    clue: SortingToolClue,
    reply: String
): CheckResult {
    val lines = reply.lines()

    if (lines.size != 2) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't parse your output: expected 2 lines.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't parse your output: expected 2 lines.")
        }
    }

    val totalRegex = """(\d+)""".toRegex()
    val totalMatchResult = totalRegex.find(lines[0])
    if (totalMatchResult == null) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Can't find number in the first line of your output.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Can't find number in the first line of your output.")
        }
    }

    val totalTokens = totalMatchResult.groupValues[1].toInt()

    val actualTotal = actualTokens.size

    if (actualTotal != totalTokens) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Total tokens ($totalTokens) are incorrect. Expected: $actualTotal.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Total tokens are incorrect.")
        }
    }

    val tokenToCount = mutableMapOf<TokenType, Int>()

    for (token in actualTokens) {
        tokenToCount[token] = (tokenToCount[token] ?: 0) + 1
    }

    val actualSortedByCount = tokenToCount.toList().sortedWith(compareBy({ it.second }, { it.first }))
    val linesWithTokens = lines.drop(1)

    if (actualSortedByCount.size != linesWithTokens.size) {
        return if (clue.revealTest) {
            CheckResult(
                false,
                "Lines with tokens (${linesWithTokens.size}) are incorrect. Expected: ${actualSortedByCount.size}.\n" +
                        revealRawTest(clue, reply)
            )
        } else {
            CheckResult(false, "Lines with tokens are incorrect.")
        }
    }

    for ((lineId, line) in linesWithTokens.withIndex()) {
        val token = tokenParser(line.substringBefore(':'))
        val info = line.substringAfter(':')

        val (actualToken, actualTimes) = actualSortedByCount[lineId]

        if (token != actualToken) {
            return if (clue.revealTest) {
                CheckResult(
                    false,
                    "Token ($token) is incorrect. Expected: $actualToken.\n" +
                            revealRawTest(clue, reply)
                )
            } else {
                CheckResult(false, "Token is incorrect.")
            }
        }

        // TODO: check info (times and percentage)
    }

    return CheckResult(true)
}

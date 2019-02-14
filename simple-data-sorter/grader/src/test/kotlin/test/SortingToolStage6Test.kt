package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution1
import java.io.File

class SortingToolStage6Test : MainMethodTest<SortingToolClue>(Solution1::class.java) {

    override fun generateTestCases(): List<TestCase<SortingToolClue>> {
        return stage4Tests() + stage5Tests() + stage6Tests()
    }

    override fun check(reply: String, clue: SortingToolClue): CheckResult {
        @Suppress("NAME_SHADOWING") var reply = reply

        val fileNameArgIdx = clue.args.indexOf("-outputFile")

        if (fileNameArgIdx != -1) {
            val fileName = clue.args[fileNameArgIdx + 1]

            reply = File(fileName).readText()
        }

        return when {
            badArgs(clue.args) -> CheckResult(true)  // TODO: test something here
            "byCount" in clue.args -> checkByCount(reply, clue)
            else -> checkNatural(reply, clue)
        }
    }

    private fun checkByCount(reply: String, clue: SortingToolClue): CheckResult {
        return when {
            "long" in clue.args -> checkByCount(parseLongTokens(clue.consoleInput), { it.toInt() }, clue, reply)
            "word" in clue.args -> checkByCount(parseWordTokens(clue.consoleInput), { it }, clue, reply)
            "line" in clue.args -> checkByCount(parseLineTokens(clue.consoleInput), { it }, clue, reply)

            else -> throw IllegalArgumentException("Bad test: no data type found")
        }
    }

    private fun checkNatural(reply: String, clue: SortingToolClue): CheckResult {
        return when {
            "long" in clue.args -> checkNatural(parseLongTokens(clue.consoleInput), ::parseLongTokens, clue, reply)
            "word" in clue.args -> checkNatural(parseWordTokens(clue.consoleInput), ::parseWordTokens, clue, reply)
            "line" in clue.args -> checkNatural(parseLineTokens(clue.consoleInput), ::parseLineTokens, clue, reply)

            else -> throw IllegalArgumentException("Bad test: no data type found")
        }
    }

    private fun badArgs(args: List<String>): Boolean {
        val unknownArgs =
            args.toSet() - setOf("-dataType", "-sortingType", "long", "word", "line", "natural", "byCount")

        if (unknownArgs.isNotEmpty()) {
            return true
        }

        if (args.last() == "-dataType" || args.last() == "-sortingType") {
            return true
        }

        return false
    }
}

private fun fileTestCase(
    input: String,
    revealTest: Boolean,
    file: String,
    vararg args: String = arrayOf("-dataType", "long")
): TestCase<SortingToolClue> {
    return TestCase(
        SortingToolClue(input, revealTest, args.toList()),
        "",
        args
    ).addFile(file, input)
}

fun stage6Tests(): List<TestCase<SortingToolClue>> {
    return listOf(
        fileTestCase(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            true,
            "input.txt",
            "-sortingType", "byCount", "-inputFile", "input.txt"
        ),
        fileTestCase(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            true,
            "data.dat",
            "-sortingType", "byCount", "-inputFile", "data.dat", "-outputFile", "out.txt"
        ),
        fileTestCase(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            false,
            "input.txt",
            "-sortingType", "natural", "-inputFile", "input.txt"
        ),
        fileTestCase(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            false,
            "data.dat",
            "-sortingType", "natural", "-inputFile", "data.dat", "-outputFile", "out.txt"
        )
    )
}

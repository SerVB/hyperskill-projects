package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution1

class SortingToolStage5Test : MainMethodTest<SortingToolClue>(Solution1::class.java) {

    override fun generateTestCases(): List<TestCase<SortingToolClue>> {
        return stage4Tests() + stage5Tests()
    }

    override fun check(reply: String, clue: SortingToolClue): CheckResult {
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

fun stage5Tests(): List<TestCase<SortingToolClue>> {
    return listOf(
        createTest(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            true,
            "-sortingType"
        ),
        createTest(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            true,
            "-sortingType", "byCount", "-dataType", "long", "-abc"
        ),
        createTest(
            """
                |1111 1111
                |22222
                |3
                |44
                """.trimMargin(),
            false,
            "-sortingType", "byCount", "-dataType", "line", "-cde"
        ),
        createTest(
            """
                |1111 1111
                |22222
                |3
                |44
                """.trimMargin(),
            false,
            "-dataType", "line", "-sortingType"
        )
    )
}

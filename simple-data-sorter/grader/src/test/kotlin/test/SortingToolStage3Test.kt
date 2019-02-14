package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution1

class SortingToolStage3Test : MainMethodTest<SortingToolClue>(Solution1::class.java) {

    override fun generateTestCases(): List<TestCase<SortingToolClue>> {
        return stage1Tests() + stage2Tests() + stage3Tests()
    }

    override fun check(reply: String, clue: SortingToolClue): CheckResult {
        return when {
            "-sortIntegers" in clue.args -> checkNatural(
                parseLongTokens(clue.consoleInput),
                ::parseLongTokens,
                clue,
                reply
            )

            "long" in clue.args -> checkForLong(clue, reply)
            "word" in clue.args -> checkForWord(clue, reply)
            "line" in clue.args -> checkForLine(clue, reply)

            else -> throw IllegalArgumentException("Bad test: no data type found")
        }
    }
}

fun stage3Tests(): List<TestCase<SortingToolClue>> {
    return listOf(
        createTest(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            true,
            "-dataType", "word", "-sortIntegers"
        ),
        createTest(
            """
                |1 -2   333 4
                |42
                |1                 1
                """.trimMargin(),
            true,
            "-sortIntegers"
        ),
        createTest(
            """
                |1111
                |22222
                |3
                |44
                """.trimMargin(),
            false,
            "-sortIntegers", "-dataType", "line"
        )
    )
}

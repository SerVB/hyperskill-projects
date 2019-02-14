package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution1

class SortingToolStage1Test : MainMethodTest<SortingToolClue>(Solution1::class.java) {

    override fun generateTestCases(): List<TestCase<SortingToolClue>> {
        return stage1Tests()
    }

    override fun check(reply: String, clue: SortingToolClue): CheckResult {
        return checkForLong(clue, reply)
    }
}

fun stage1Tests(): List<TestCase<SortingToolClue>> {
    return listOf(
        createTest(
            """
                |1 -2   33 4
                |42
                |1                 1
                """.trimMargin(),
            true
        ),
        createTest("1 2 2 3 4 5 5", true),
        createTest("1 1 2 2 3 4 4 4", false)
    )
}

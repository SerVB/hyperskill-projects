package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution567
import test.FlashcardsStage5Test.Companion.createStage5Tests

class FlashcardsStage6Test : MainMethodTest<FlashcardsStage567Clue>(Solution567::class.java) {

    override fun generateTestCases(): List<TestCase<FlashcardsStage567Clue>> {
        return createStage6Tests() + createStage5Tests() + generateOldTests()
    }

    /**
     * This stage is auto-graded.
     *
     * The grader skips empty lines.
     *
     * Every action must start with words "input the action".
     *
     * The behaviour of "ask" action is the same as the behaviour of the previous stage.
     *
     * Please check the console example.
     */
    override fun check(reply: String, clue: FlashcardsStage567Clue): CheckResult {
        return checkStage567(reply, clue)
    }

    companion object {
        fun createStage6Tests(): List<TestCase<FlashcardsStage567Clue>> {
            // TODO: how to remove files? Now at least rewrite at the first test:

            return listOf(
                createFlashcardsStage567TestCase(
                    listOf(
                        Log("lastLog.txt"),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Add("Great Britain", "London"),
                        Ask(listOf("?")),
                        HardestCard(),
                        ResetStats(),
                        Log("lastLog.txt"),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Add("Great Britain", "London"),
                        Add("France", "Paris"),
                        Add("Russia", "Moscow"),
                        Ask(listOf("?", "?")),
                        HardestCard(),
                        Log("lastLog.txt"),
                        ResetStats(),
                        Exit()
                    ),
                    false
                )
            )
        }
    }
}

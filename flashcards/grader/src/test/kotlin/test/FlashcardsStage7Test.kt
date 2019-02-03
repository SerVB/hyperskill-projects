package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution567
import test.FlashcardsStage5Test.Companion.createStage5Tests
import test.FlashcardsStage6Test.Companion.createStage6Tests

class FlashcardsStage7Test : MainMethodTest<FlashcardsStage567Clue>(Solution567::class.java) {

    override fun generateTestCases(): List<TestCase<FlashcardsStage567Clue>> {
        return createStage7Tests() + createStage6Tests() + createStage5Tests() + generateOldTests()
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
        fun createStage7Tests(): List<TestCase<FlashcardsStage567Clue>> {
            // TODO: how to remove files? Now at least rewrite at the first test:

            return listOf(
                createFlashcardsStage567TestCase(
                    listOf(
                        Export("capitalsNew.txt"),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Add("Great Britain", "London"),
                        Exit()
                    ),
                    true,
                    "-export", "capitalsNew.txt"
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Import("capitalsNew.txt", 1),
                        Add("Russia", "Moscow"),
                        Exit()
                    ),
                    true,
                    "-export", "capitalsNew.txt"
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Add("France", "Paris"),
                        Export("capitalsNew.txt"),
                        Exit()
                    ),
                    true,
                    "-import", "capitalsNew.txt"
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Import("capitalsNew.txt", 3),
                        Exit()
                    ),
                    true
                )
            )
        }
    }
}

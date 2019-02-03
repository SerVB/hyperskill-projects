package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution567

class FlashcardsStage5Test : MainMethodTest<FlashcardsStage567Clue>(Solution567::class.java) {

    override fun generateTestCases(): List<TestCase<FlashcardsStage567Clue>> {
        return createStage5Tests() + generateOldTests()
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
        fun createStage5Tests(): List<TestCase<FlashcardsStage567Clue>> {
            // TODO: how to remove files? Now at least rewrite at the first test:

            return listOf(
                createFlashcardsStage567TestCase(
                    listOf(
                        Export("capitals.txt"),
                        Export("capitalsNew.txt"),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Add("Great Britain", "London"),
                        Remove("Wakanda"),
                        Import("capitals.txt", 0),
                        Ask(listOf("London")),
                        Export("capitalsNew.txt"),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Import("capitalsNew.txt", 1),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Import("capitalsNew.txt", 1),
                        Add("France", "Paris"),
                        Add("Russia", "Moscow"),
                        Export("capitalsNew.txt"),
                        Import("capitalsNew.txt", 3),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Import("capitalsNew.txt", 3),
                        Add("Japan", "Tokyo"),
                        Export("capitalsNew.txt"),
                        Import("capitalsNew.txt", 4),
                        Exit()
                    ),
                    false
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Add("Great Britain", "London"),
                        Add("France", "Paris"),
                        Add("Russia", "Moscow"),
                        Add("Japan", "Tokyo"),
                        Add("London", "Big Ben"),
                        Remove("London"),
                        Export("capitalsNew.txt"),
                        Import("capitalsNew.txt", 4),
                        Exit()
                    ),
                    true
                ),
                createFlashcardsStage567TestCase(
                    listOf(
                        Add("Great Britain", "London"),
                        Add("France", "Paris"),
                        Add("Russia", "Moscow"),
                        Add("Japan", "Tokyo"),
                        Add("London", "Big Ben"),
                        Remove("London"),
                        Remove("Russia"),
                        Remove("Japan"),
                        Export("capitalsNew.txt"),
                        Import("capitalsNew.txt", 2),
                        Exit()
                    ),
                    false
                )
            )
        }
    }
}

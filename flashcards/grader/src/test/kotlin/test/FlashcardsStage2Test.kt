package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution2
import test.FlashcardsStage2Answer.RIGHT
import test.FlashcardsStage2Answer.WRONG

data class FlashcardsStage2Clue(
    val consoleInput: String,
    val answer: FlashcardsStage2Answer,
    val revealTest: Boolean = false
)

enum class FlashcardsStage2Answer(val word: String) {
    RIGHT("right"),
    WRONG("wrong");
}

class FlashcardsStage2Test : MainMethodTest<FlashcardsStage2Clue>(Solution2::class.java) {

    override fun generateTestCases(): List<TestCase<FlashcardsStage2Clue>> {
        return listOf(
            createTestCase("a purring animal\ncat\ncat\n", RIGHT, true),
            createTestCase("a purring animal\ncat\n????\n", WRONG, true),
            createTestCase("a barking animal\ndog\ncat\n", WRONG),
            createTestCase("a barking animal\ndog\ndog\n", RIGHT)
        )
    }

    override fun check(reply: String, clue: FlashcardsStage2Clue): CheckResult {
        val words = reply.lowerWords()

        val hasRight = RIGHT.word.toLowerCase() in words
        val hasWrong = WRONG.word.toLowerCase() in words

        if ((hasRight || !hasWrong) && clue.answer == WRONG) {
            return if (clue.revealTest) {
                CheckResult(
                    false,
                    revealRawTest(clue.consoleInput, reply) + "Expected the \"${WRONG.word}\" word."
                )
            } else {
                CheckResult(false)
            }
        }

        if ((hasWrong || !hasRight) && clue.answer == RIGHT) {
            return if (clue.revealTest) {
                CheckResult(
                    false,
                    revealRawTest(clue.consoleInput, reply) + "Expected the \"${RIGHT.word}\" word."
                )
            } else {
                CheckResult(false)
            }
        }

        return CheckResult(true)
    }

    companion object {
        private fun createTestCase(
            consoleInput: String,
            answer: FlashcardsStage2Answer,
            revealTest: Boolean = false
        ): TestCase<FlashcardsStage2Clue> {
            return TestCase(
                FlashcardsStage2Clue(consoleInput, answer, revealTest),
                consoleInput
            )
        }
    }
}

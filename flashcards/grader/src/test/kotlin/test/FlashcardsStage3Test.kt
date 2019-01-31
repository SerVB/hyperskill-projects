package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution3

data class FlashcardsStage3Clue(
    val consoleInput: String,
    val revealTest: Boolean = false
)

class FlashcardsStage3Test : MainMethodTest<FlashcardsStage3Clue>(Solution3::class.java) {

    override fun generateTestCases(): List<TestCase<FlashcardsStage3Clue>> {
        return listOf(
            createTestCase(
                "2\n" +
                        "black\nwhite\n" +
                        "white\nblack\n" +
                        "white\n" +
                        "blue\n",
                true
            ),
            createTestCase(
                "5\n" +
                        "1\n1\n" +
                        "2\n2\n" +
                        "3\n3\n" +
                        "4\n4\n" +
                        "5\n5\n" +
                        "1\n" +
                        "2\n" +
                        "3\n" +
                        "4\n" +
                        "5\n",
                true
            ),
            createTestCase(
                "5\n" +
                        "1\n1\n" +
                        "2\n2\n" +
                        "3\n3\n" +
                        "4\n4\n" +
                        "5\n5\n" +
                        "5\n" +
                        "4\n" +
                        "3\n" +
                        "2\n" +
                        "1\n",
                true
            ),
            createTestCase(
                "4\n" +
                        "11\n21\n" +
                        "12\n22\n" +
                        "13\n23\n" +
                        "14\n24\n" +
                        "21\n" +
                        "22\n" +
                        "33333\n" +
                        "24\n"
            ),
            createTestCase(
                "2\n" +
                        "a brother of one's parent\nuncle\n" +
                        "a part of the body where the foot and the leg meet\nankle\n" +
                        "ankle\n" +
                        "??\n"
            )
        )
    }

    /**
     * This stage is auto-graded.
     *
     * The grader will input 1 line with the card number.
     *
     * Then it will input [card number] pairs of lines (card, definition).
     *
     * Finally, you should ask definitions of every card:
     * Ask the question with the quoted card (example: Print the definition of "black").
     * The grader will input an answer.
     * On the next line, you should grade the question. The grade must contain "correct answer" if the answer is correct, otherwise, it must contain "wrong answer".  If the answer is wrong, you should also write the quoted correct answer (example: wrong answer, the correct one is "black").
     * If there are remaining cards, ask the definition of the next one in the same line.
     *
     * You can output as many empty lines as you want: the grader won't see them.
     *
     * Please check the console example.
     */
    override fun check(reply: String, clue: FlashcardsStage3Clue): CheckResult {
        val cards = clue.consoleInput.parseConsoleInputWithoutActions()

        val inputLines = clue.consoleInput.lines().filter { it.isNotEmpty() }
        val replyLines = reply.lines().filter { it.isNotEmpty() }

        val questionCount = inputLines[0].toInt()

        val askedCards = mutableSetOf<String>()

        val questions = replyLines.takeLast(questionCount + 1).take(questionCount)
        val answers = inputLines.takeLast(questionCount)
        val grades = replyLines.takeLast(questionCount)

        for (questionId in 0 until questionCount) {
            val question = questions[questionId]
            val answer = answers[questionId]
            val grade = grades[questionId]

            val hasCorrect = CORRECT.toLowerCase() in grade.toLowerCase()
            val hasWrong = WRONG.toLowerCase() in grade.toLowerCase()

            val card = question.substringBeforeLast('"').substringAfterLast('"')

            askedCards.add(card)

            val correctAnswer = cards.cardToDefinition[card] == answer

            if (correctAnswer && (hasWrong || !hasCorrect)) {
                return if (clue.revealTest) {
                    val feedback = "Mistake:\n\n" +
                            "Question:\n$question\n" +
                            "Answer:\n$answer\n" +
                            "Grade:\n$grade\n" +
                            "The grade doesn't say the answer is correct."

                    CheckResult(
                        false,
                        revealRawTest(clue.consoleInput, reply) +
                                revealTestWithoutActions(questions, answers, grades) +
                                feedback
                    )
                } else {
                    CheckResult(false)
                }
            }

            if (!correctAnswer && (hasCorrect || !hasWrong)) {
                return if (clue.revealTest) {
                    val feedback = "Mistake:\n\n" +
                            "Question:\n$question\n" +
                            "Answer:\n$answer\n" +
                            "Grade:\n$grade\n" +
                            "The grade doesn't say the answer is wrong."

                    CheckResult(
                        false,
                        revealRawTest(clue.consoleInput, reply) +
                                revealTestWithoutActions(questions, answers, grades) +
                                feedback
                    )
                } else {
                    CheckResult(false)
                }
            }

            if (!correctAnswer) {
                val suggestedDefinition = grade.substringAfter('"').substringBefore('"')

                if (suggestedDefinition != cards.cardToDefinition[card]) {
                    return if (clue.revealTest) {
                        val feedback = "Mistake:\n\n" +
                                "Question:\n$question\n" +
                                "Answer:\n$answer\n" +
                                "Grade:\n$grade\n" +
                                "The grade says the \"$suggestedDefinition\" is correct but it isn't."

                        CheckResult(
                            false,
                            revealRawTest(clue.consoleInput, reply) +
                                    revealTestWithoutActions(questions, answers, grades) +
                                    feedback
                        )
                    } else {
                        CheckResult(false)
                    }
                }
            }
        }

        if (askedCards.size != questionCount) {
            return if (clue.revealTest) {
                CheckResult(
                    false,
                    "You haven't asked about some cards: ${cards.cardToDefinition.keys - askedCards}."
                )
            } else {
                CheckResult(false, "You haven't asked about some cards")
            }
        }

        return CheckResult(true)
    }

    companion object {
        private const val CORRECT = "correct answer"
        private const val WRONG = "wrong answer"

        private fun createTestCase(
            consoleInput: String,
            revealTest: Boolean = false
        ): TestCase<FlashcardsStage3Clue> {
            return TestCase(
                FlashcardsStage3Clue(consoleInput, revealTest),
                consoleInput
            )
        }
    }
}


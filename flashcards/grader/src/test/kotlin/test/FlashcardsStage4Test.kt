package test

import org.hyperskill.hstest.stage.MainMethodTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase
import solution.Solution4

data class FlashcardsStage4Clue(
    val consoleInput: String,
    val revealTest: Boolean = false
)

class FlashcardsStage4Test : MainMethodTest<FlashcardsStage4Clue>(Solution4::class.java) {

    override fun generateTestCases(): List<TestCase<FlashcardsStage4Clue>> {
        return listOf(
            createTestCase(
                "2\n" +
                        "a brother of one's parent\nuncle\n" +
                        "a part of the body where the foot and the leg meet\nankle\n" +
                        "ankle\n" +
                        "??\n",
                true
            ),
            createTestCase(
                "2\n" +
                        "black\nwhite\n" +
                        "black\n" +
                        "black\n" +
                        "white\nblack\n" +
                        "white\n" +
                        "blue\n"
            ),
            createTestCase(
                "2\n" +
                        "black\nwhite\n" +
                        "white\nblack\n" +
                        "white\n" +
                        "blue\n"
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
                        "5\n"
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
                        "1\n"
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
                "4\n" +
                        "11\n21\n" +
                        "12\n22\n" +
                        "12\n" +
                        "13\n23\n" +
                        "14\n24\n" +
                        "21\n" +
                        "22\n" +
                        "33333\n" +
                        "24\n",
                true
            )
        )
    }

    /**
     * This stage is auto-graded.
     *
     * The behaviour is almost the same as the one in the previous stage.
     *
     * You should print the quoted original card after the correct one (example: the correct one is "uncle", you've just written a definition of "a part of the body where the foot and the leg meet" card).
     *
     * Also, the grader can input already existing cards. You should ask for the card until the unique card is inputted.
     *
     * Please check the console example.
     */
    override fun check(reply: String, clue: FlashcardsStage4Clue): CheckResult {
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

            if (!correctAnswer && answer in cards.definitionToCard) {
                val originalCard = grade
                    .substringAfter('"')
                    .substringAfter('"')
                    .substringAfter('"')
                    .substringBefore('"')

                if (originalCard != cards.definitionToCard[answer]) {
                    return if (clue.revealTest) {
                        val feedback = "Mistake:\n\n" +
                                "Question:\n$question\n" +
                                "Answer:\n$answer\n" +
                                "Grade:\n$grade\n" +
                                "The grade says the \"$originalCard\" card is the original one but it isn't."

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
        ): TestCase<FlashcardsStage4Clue> {
            return TestCase(
                FlashcardsStage4Clue(consoleInput, revealTest),
                consoleInput
            )
        }
    }
}
package test

import java.io.File

open class ActionCheckResult(val isCorrect: Boolean, val feedback: String? = null)

sealed class Action {
    abstract fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult

    abstract fun generateInput(): List<String>
}

class Add(private val card: String, private val definition: String) : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        if (replyLines.size != 3) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 3 lines.\n" +
                            "Lines: ${replyLines.joinToString(prefix = "(", postfix = ")")}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 3 lines."
                )
            }
        }

        val lastLine = replyLines.last()
        val lastLineMeansAddition = '(' in lastLine

        val cardAlreadyExists = card in cards.cardToDefinition

        if (lastLineMeansAddition && cardAlreadyExists) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": the \"$lastLine\" line means you add the card but " +
                            "the card already exists."
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you add the card which already exists."
                )
            }
        }

        if (!lastLineMeansAddition && !cardAlreadyExists) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": the \"$lastLine\" line means you don't add the card but " +
                            "the card doesn't already exist so you should."
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't add the card which doesn't already exist."
                )
            }
        }

        if (!cardAlreadyExists) {
            cards.cardToDefinition[card] = definition
            cards.definitionToCard[definition] = card
        }

        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME, card, definition)
    }

    companion object {
        private const val ACTION_NAME = "add"
    }
}

class Remove(private val card: String) : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        if (replyLines.size != 2) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines.\n" +
                            "Lines: ${replyLines.joinToString(prefix = "(", postfix = ")")}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines."
                )
            }
        }

        val lastLine = replyLines.last()
        val lastLineMeansDeletion = "can't remove" !in lastLine.toLowerCase()

        val cardAlreadyExists = card in cards.cardToDefinition

        if (lastLineMeansDeletion && !cardAlreadyExists) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": the \"$lastLine\" line means you remove the card but " +
                            "the card doesn't already exist."
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you remove the card which doesn't already exist."
                )
            }
        }

        if (!lastLineMeansDeletion && cardAlreadyExists) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": the \"$lastLine\" line means you don't remove the card but " +
                            "the card already exists so you should."
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't remove the card which already exists."
                )
            }
        }

        if (cardAlreadyExists) {
            val definition = cards.cardToDefinition[card]
            cards.cardToDefinition.remove(card)
            cards.definitionToCard.remove(definition)
        }

        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME, card)
    }

    companion object {
        private const val ACTION_NAME = "remove"
    }
}

class Import(private val fileName: String, private val expectedCardCount: Int) : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        if (replyLines.size != 2) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines.\n" +
                            "Lines: ${replyLines.joinToString(prefix = "(", postfix = ")")}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines."
                )
            }
        }

        val cardCounts = replyLines
            .last()
            .split(" ")
            .filter { it.isNotEmpty() && it.all { c -> c.isDigit() } }

        if (cardCounts.size != 1) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 1 number.\n" +
                            "Line: ${replyLines.last()}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 1 number."
                )
            }
        }

        val cardCount = cardCounts.first().toInt()

        if (cardCount != expectedCardCount) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you import an unexpected ($cardCount) amount of cards. " +
                            "Expected: $expectedCardCount."
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you import an unexpected amount of cards."
                )
            }
        }

        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME, fileName)
    }

    companion object {
        private const val ACTION_NAME = "import"
    }
}

class Export(private val fileName: String) : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        if (replyLines.size != 2) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines.\n" +
                            "Lines: ${replyLines.joinToString(prefix = "(", postfix = ")")}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines."
                )
            }
        }

        val cardCounts = replyLines
            .last()
            .split(" ")
            .filter { it.isNotEmpty() && it.all { c -> c.isDigit() } }

        if (cardCounts.isEmpty()) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output any numbers.\n" +
                            "Line: ${replyLines.last()}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output any numbers."
                )
            }
        }

        val file = File(fileName)

        if (!file.exists()) {
            ActionCheckResult(
                false,
                "Action \"$ACTION_NAME\": you don't create the file."
            )
        }

        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME, fileName)
    }

    companion object {
        private const val ACTION_NAME = "export"
    }
}

class Exit : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME)
    }

    companion object {
        private const val ACTION_NAME = "exit"
    }
}

class Ask(private val answers: List<String>) : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        val questionCount = answers.size

        val questions = replyLines.takeLast(questionCount + 1).take(questionCount)
        val grades = replyLines.takeLast(questionCount)

        for (questionId in 0 until questionCount) {
            val question = questions[questionId]
            val answer = answers[questionId]
            val grade = grades[questionId]

            val hasCorrect = CORRECT.toLowerCase() in grade.toLowerCase()
            val hasWrong = WRONG.toLowerCase() in grade.toLowerCase()

            val card = question.substringBeforeLast('"').substringAfterLast('"')

            val correctAnswer = cards.cardToDefinition[card] == answer

            if (correctAnswer && (hasWrong || !hasCorrect)) {
                return if (revealTest) {
                    val feedback = "Mistake:\n\n" +
                            "Question:\n$question\n" +
                            "Answer:\n$answer\n" +
                            "Grade:\n$grade\n" +
                            "The grade doesn't say the answer is correct."

                    ActionCheckResult(
                        false,
                        revealTestWithoutActions(questions, answers, grades) +
                                feedback
                    )
                } else {
                    ActionCheckResult(false)
                }
            }

            if (!correctAnswer && (hasCorrect || !hasWrong)) {
                return if (revealTest) {
                    val feedback = "Mistake:\n\n" +
                            "Question:\n$question\n" +
                            "Answer:\n$answer\n" +
                            "Grade:\n$grade\n" +
                            "The grade doesn't say the answer is wrong."

                    ActionCheckResult(
                        false,
                        revealTestWithoutActions(questions, answers, grades) +
                                feedback
                    )
                } else {
                    ActionCheckResult(false)
                }
            }

            if (!correctAnswer) {
                val suggestedDefinition = grade.substringAfter('"').substringBefore('"')

                if (suggestedDefinition != cards.cardToDefinition[card]) {
                    return if (revealTest) {
                        val feedback = "Mistake:\n\n" +
                                "Question:\n$question\n" +
                                "Answer:\n$answer\n" +
                                "Grade:\n$grade\n" +
                                "The grade says the \"$suggestedDefinition\" is correct but it isn't."

                        ActionCheckResult(
                            false,
                            revealTestWithoutActions(questions, answers, grades) +
                                    feedback
                        )
                    } else {
                        ActionCheckResult(false)
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
                    return if (revealTest) {
                        val feedback = "Mistake:\n\n" +
                                "Question:\n$question\n" +
                                "Answer:\n$answer\n" +
                                "Grade:\n$grade\n" +
                                "The grade says the \"$originalCard\" card is the original one but it isn't."

                        ActionCheckResult(
                            false,
                            revealTestWithoutActions(questions, answers, grades) +
                                    feedback
                        )
                    } else {
                        ActionCheckResult(false)
                    }
                }
            }
        }

        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME, answers.size.toString()) + answers
    }

    companion object {
        private const val ACTION_NAME = "ask"

        private const val CORRECT = "correct answer"
        private const val WRONG = "wrong answer"
    }
}

class Log(private val fileName: String) : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        if (replyLines.size != 2) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines.\n" +
                            "Lines: ${replyLines.joinToString(prefix = "(", postfix = ")")}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 2 lines."
                )
            }
        }

        val file = File(fileName)

        if (!file.exists()) {
            ActionCheckResult(
                false,
                "Action \"$ACTION_NAME\": you don't create the file."
            )
        }

        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME, fileName)
    }

    companion object {
        private const val ACTION_NAME = "log"
    }
}

// TODO: check the value
class HardestCard : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        if (replyLines.size != 1) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 1 line.\n" +
                            "Lines: ${replyLines.joinToString(prefix = "(", postfix = ")")}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output exactly 1 line."
                )
            }
        }

        val cardCounts = replyLines
            .last()
            .split(" ")
            .filter { it.isNotEmpty() && it.all { c -> c.isDigit() } }

        if (cardCounts.isEmpty()) {
            return if (revealTest) {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output any numbers.\n" +
                            "Line: ${replyLines.last()}"
                )
            } else {
                ActionCheckResult(
                    false,
                    "Action \"$ACTION_NAME\": you don't output any numbers."
                )
            }
        }

        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME)
    }

    companion object {
        private const val ACTION_NAME = "hardest card"
    }
}

class ResetStats : Action() {

    override fun check(revealTest: Boolean, cards: Cards, replyLines: List<String>): ActionCheckResult {
        return ActionCheckResult(true)
    }

    override fun generateInput(): List<String> {
        return listOf(ACTION_NAME)
    }

    companion object {
        private const val ACTION_NAME = "reset stats"
    }
}

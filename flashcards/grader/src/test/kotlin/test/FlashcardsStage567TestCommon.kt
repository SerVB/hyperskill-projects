package test

import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testcase.TestCase

fun checkStage567(reply: String, clue: FlashcardsStage567Clue): CheckResult {
    val actionLines = splitReplyToActions(reply)

    if (actionLines.size != clue.actions.size) {
        val feedback = "${actionLines.size} are found but ${clue.actions.size} actions have been inputted."

        return if (clue.revealTest) {
            CheckResult(false, revealRawTest(clue.consoleInput, reply) + feedback)
        } else {
            CheckResult(false, feedback)
        }
    }

    val cards = Cards()

    val checks = actionLines
        .zip(clue.actions)
        .map { (lines, action) -> action.check(clue.revealTest, cards, lines) }

    val firstUnsuccessful = checks.firstOrNull { !it.isCorrect }

    if (firstUnsuccessful != null) {
        return if (clue.revealTest) {
            CheckResult(false, revealRawTest(clue.consoleInput, reply) + firstUnsuccessful.feedback)
        } else {
            CheckResult(false, firstUnsuccessful.feedback)
        }
    }

    return CheckResult(true)
}

class FlashcardsStage567Clue(
    val consoleInput: String,
    val actions: List<Action>,
    val revealTest: Boolean
)

fun createFlashcardsStage567TestCase(
    actions: List<Action>,
    revealTest: Boolean = false,
    vararg args: String
): TestCase<FlashcardsStage567Clue> {
    val consoleInput = actions.flatMap { it.generateInput() }.joinToString("\n")

    return TestCase(
        FlashcardsStage567Clue(consoleInput, actions, revealTest),
        consoleInput
    ).addArgument(args)
}

fun splitReplyToActions(reply: String): List<List<String>> {
    val answer = mutableListOf<List<String>>()

    var lines = reply.lines().filter { it.isNotEmpty() }

    while (lines.isNotEmpty()) {
        val lastAction = lines.takeLastWhile { "input the action" !in it.toLowerCase() }

        if (lines.size > lastAction.size) {
            answer.add(lastAction)
            lines = lines.dropLast(lastAction.size + 1)
        } else {
            break  // if the first action doesn't contain "input the action" then it's not an action
            // for example, it can be info about the program or lines about run arguments
        }
    }

    return answer.reversed()
}

class Cards(
    val cardToDefinition: MutableMap<String, String> = mutableMapOf(),
    val definitionToCard: MutableMap<String, String> = mutableMapOf()
)

fun generateOldTests(): List<TestCase<FlashcardsStage567Clue>> {
    return listOf(
        createFlashcardsStage567TestCase(
            listOf(
                Add("a brother of one's parent", "uncle"),
                Add("a part of the body where the foot and the leg meet", "ankle"),
                Ask(listOf("ankle", "??")),
                Exit()
            ),
            true
        ),
        createFlashcardsStage567TestCase(
            listOf(
                Add("black", "white"),
                Add("black", "pink"),
                Add("black", "yellow"),
                Ask(listOf("white")),
                Exit()
            ),
            true
        ),
        createFlashcardsStage567TestCase(
            listOf(
                Add("11", "21"),
                Add("12", "22"),
                Add("12", "222"),
                Ask(listOf("22", "22")),
                Exit()
            ),
            false
        )
    )
}

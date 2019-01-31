package test

data class ParsedCards(
    val cardToDefinition: Map<String, String>,
    val definitionToCard: Map<String, String>
)

fun revealRawTest(consoleInput: String, reply: String): String {
    return "Input:\n$consoleInput\nYour output:\n$reply\n\n"
}

fun String.lowerWords(): Set<String> {
    val lowerReply = this.trim().toLowerCase()

    val onlyLetters = lowerReply.map { if (it.isLetter()) it else ' ' }.joinToString("")

    return onlyLetters.split(" ").filter { it.isNotEmpty() }.toSet()
}

fun String.parseConsoleInputWithoutActions(): ParsedCards {
    val lines = this.lines()

    val cardCount = lines[0].toInt()

    var lastLineWithCardId = 2 * cardCount

    val cardToDefinition = mutableMapOf<String, String>()
    val definitionToCard = mutableMapOf<String, String>()

    var lineId = 1

    while (lineId <= lastLineWithCardId) {
        val card = lines[lineId]
        val definition = lines[lineId + 1]

        if (card in cardToDefinition) {
            lastLineWithCardId += 1
            lineId += 1
        } else {
            cardToDefinition[card] = definition
            definitionToCard[definition] = card

            lineId += 2
        }
    }

    return ParsedCards(cardToDefinition = cardToDefinition, definitionToCard = definitionToCard)
}

fun revealTestWithoutActions(
    questions: List<String>,
    answers: List<String>,
    grades: List<String>
): String {
    val count = minOf(questions.size, answers.size, grades.size)

    return "Questions:\n\n" +
            (0 until count).joinToString("\n") {
                "Question:\n${questions[it]}\n" +
                        "Answer:\n${answers[it]}\n" +
                        "Grade:\n${grades[it]}\n"
            } +
            "\n"
}

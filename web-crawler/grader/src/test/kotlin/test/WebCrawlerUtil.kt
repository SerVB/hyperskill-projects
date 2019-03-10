package test

import org.assertj.swing.fixture.AbstractJComponentFixture
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase

const val PORT = 25555

class WebCrawlerClue(val feedback: String, val checker: () -> Boolean)

fun createWebCrawlerTest(feedback: String, checker: () -> Boolean): TestCase<WebCrawlerClue> {
    return TestCase<WebCrawlerClue>()
        .setAttach(
            WebCrawlerClue(
                feedback = feedback,
                checker = checker
            )
        )
}

fun checkWebCrawlerTest(reply: String, clue: WebCrawlerClue): CheckResult {
    return try {
        val result = clue.checker()

        CheckResult(result, clue.feedback)
    } catch (e: AssertionError) {
        CheckResult(false, clue.feedback)
    }
}

fun htmlTextsAreEqual(source: String, inTextField: String): Boolean {
    fun String.formatted(): String {
        return this.trim().replace("\r\n", "\n").replace("\r", "\n")
    }

    return source.formatted() == inTextField.formatted()
}

fun <ComponentType : AbstractJComponentFixture<*, *, *>> requireExistingComponent(
    requirements: ComponentRequirements<ComponentType>
): ComponentType {
    return requireNotNull(requirements.suitableComponent) {
        "Must check for the '${requirements.name}' component existence before this test"
    }
}

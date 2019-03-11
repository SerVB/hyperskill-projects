package test

import org.assertj.swing.fixture.JTextComponentFixture
import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase
import solution.Solution1

class WebCrawlerStage1Test : SwingTest<WebCrawlerClue>(Solution1()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        val textArea = ComponentRequirements("TextArea", isEnabled = false) { window.textBox(it) }

        return frameTests(::frame) +
                existenceTests(textArea) +
                componentsAreEnabledTests(textArea) +
                stage1Tests(textAreaRequirements = textArea)
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

fun stage1Tests(textAreaRequirements: ComponentRequirements<JTextComponentFixture>): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest("'${textAreaRequirements.name}' should contain text 'HTML code?'") {
            val textArea = textAreaRequirements.requireExistingComponent()

            return@createWebCrawlerTest ("html code?" in textArea.text()?.toLowerCase().orEmpty()).toCheckResult()
        }
    )
}

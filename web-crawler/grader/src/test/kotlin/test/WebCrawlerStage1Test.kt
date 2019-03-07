package test

import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase
import solution.Solution1

class WebCrawlerStage1Test : PublicSwingTest<WebCrawlerClue>(Solution1()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        return stage1Tests(swingTest = this, textAreaName = "TextArea")
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

private fun stage1Tests(
    swingTest: PublicSwingTest<WebCrawlerClue>,
    textAreaName: String
): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest("Window is not visible") { swingTest.frame.isVisible },
        createWebCrawlerTest("Window title is empty") { swingTest.frame.title.isNotEmpty() },
        createWebCrawlerTest("There is no text component with the '$textAreaName' name") {
            SwingTest.checkExistence { swingTest.window.textBox(textAreaName) }
        },
        createWebCrawlerTest("'$textAreaName' should be disabled") {
            !(swingTest.window.textBox(textAreaName).isEnabled)
        },
        createWebCrawlerTest("'$textAreaName' should contain text 'HTML code?'") {
            "html code?" in swingTest.window.textBox(textAreaName).text()?.toLowerCase().orEmpty()
        }
    )
}

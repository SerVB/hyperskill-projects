package test

import org.assertj.swing.fixture.JButtonFixture
import org.assertj.swing.fixture.JTextComponentFixture
import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase
import solution.Solution2

class WebCrawlerStage2Test : SwingTest<WebCrawlerClue>(Solution2()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        val htmlText = ComponentRequirements("HtmlTextArea", isEnabled = false) { window.textBox(it) }
        val urlText = ComponentRequirements("UrlTextField", isEnabled = true) { window.textBox(it) }
        val getButton = ComponentRequirements("RunButton", isEnabled = true) { window.button(it) }

        return frameTests(::frame) +
                existenceTests(htmlText, urlText, getButton) +
                componentsAreEnabledTests(htmlText, urlText, getButton) +
                stage2Tests(
                    htmlTextAreaRequirements = htmlText,
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText
                )
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

fun stage2Tests(
    htmlTextAreaRequirements: ComponentRequirements<JTextComponentFixture>,
    getTextButtonRequirements: ComponentRequirements<JButtonFixture>,
    locationTextFieldRequirements: ComponentRequirements<JTextComponentFixture>
): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest("HTML code your app shows is wrong") {
            val locationTextField = locationTextFieldRequirements.requireExistingComponent()
            val getTextButton = getTextButtonRequirements.requireExistingComponent()
            val htmlTextArea = htmlTextAreaRequirements.requireExistingComponent()

            return@createWebCrawlerTest pages
                .asSequence()
                .map { (url, pageProperties) ->
                    locationTextField.setText(url)

                    getTextButton.click()

                    val textInTextArea = htmlTextArea.text().orEmpty()

                    return@map htmlTextsAreEqual(pageProperties.content, textInTextArea)
                }
                .all { it }
                .toCheckResult()
        }.withLocalhostPagesOn(PORT)
    )
}

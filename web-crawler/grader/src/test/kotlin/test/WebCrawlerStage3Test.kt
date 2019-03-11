package test

import org.assertj.swing.fixture.JButtonFixture
import org.assertj.swing.fixture.JLabelFixture
import org.assertj.swing.fixture.JTextComponentFixture
import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase
import solution.Solution3

class WebCrawlerStage3Test : SwingTest<WebCrawlerClue>(Solution3()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        val htmlText = ComponentRequirements("HtmlTextArea", isEnabled = false) { window.textBox(it) }
        val urlText = ComponentRequirements("UrlTextField", isEnabled = true) { window.textBox(it) }
        val getButton = ComponentRequirements("RunButton", isEnabled = true) { window.button(it) }
        val titleLabel = ComponentRequirements("TitleLabel", isEnabled = true) { window.label(it) }

        return frameTests(::frame) +
                existenceTests(htmlText, urlText, getButton, titleLabel) +
                componentsAreEnabledTests(htmlText, urlText, getButton, titleLabel) +
                stage2Tests(
                    htmlTextAreaRequirements = htmlText,
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText
                ) +
                stage3Tests(
                    titleLabelRequirements = titleLabel,
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText
                )
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

fun stage3Tests(
    titleLabelRequirements: ComponentRequirements<JLabelFixture>,
    getTextButtonRequirements: ComponentRequirements<JButtonFixture>,
    locationTextFieldRequirements: ComponentRequirements<JTextComponentFixture>
): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest("Title your app shows is wrong") {
            val locationTextField = locationTextFieldRequirements.requireExistingComponent()
            val getTextButton = getTextButtonRequirements.requireExistingComponent()
            val titleLabel = titleLabelRequirements.requireExistingComponent()

            return@createWebCrawlerTest pages
                .asSequence()
                .map { (url, pageProperties) ->
                    locationTextField.setText(url)

                    getTextButton.click()

                    val titleInLabel = titleLabel.text().orEmpty()

                    return@map titleInLabel == pageProperties.title
                }
                .all { it }
                .toCheckResult()
        }.withLocalhostPagesOn(PORT)
    )
}

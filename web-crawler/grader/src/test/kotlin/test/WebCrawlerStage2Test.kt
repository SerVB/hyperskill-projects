package test

import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase
import solution.Solution2

class WebCrawlerStage2Test : PublicSwingTest<WebCrawlerClue>(Solution2()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        return commonTests(swingTest = this) +
                stage2Tests(
                    swingTest = this,
                    htmlTextAreaName = "HtmlTextArea",
                    getTextButtonName = "GetTextButton",
                    locationTextFieldName = "LocationTextField"
                )
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

fun stage2Tests(
    swingTest: PublicSwingTest<WebCrawlerClue>,
    htmlTextAreaName: String,
    getTextButtonName: String,
    locationTextFieldName: String
): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest("There is no text component with the '$htmlTextAreaName' name") {
            SwingTest.checkExistence { swingTest.window.textBox(htmlTextAreaName) }
        },
        createWebCrawlerTest("There is no text component with the '$getTextButtonName' name") {
            SwingTest.checkExistence { swingTest.window.button(getTextButtonName) }
        },
        createWebCrawlerTest("There is no text component with the '$locationTextFieldName' name") {
            SwingTest.checkExistence { swingTest.window.textBox(locationTextFieldName) }
        },
        createWebCrawlerTest("'$locationTextFieldName' should be enabled") {
            swingTest.window.textBox(locationTextFieldName).isEnabled
        },
        createWebCrawlerTest("'$getTextButtonName' should be enabled") {
            swingTest.window.button(getTextButtonName).isEnabled
        },
        createWebCrawlerTest("'$htmlTextAreaName' should be disabled") {
            !(swingTest.window.textBox(htmlTextAreaName).isEnabled)
        },
        createWebCrawlerTest("HTML code your app shows is wrong") {
            val urlText = swingTest.window.textBox(locationTextFieldName)!!
            val getButton = swingTest.window.button(getTextButtonName)!!
            val htmlText = swingTest.window.textBox(htmlTextAreaName)!!

            return@createWebCrawlerTest pages
                .asSequence()
                .map { (url, text) ->
                    urlText.setText("localhost:$PORT$url")

                    getButton.click()

                    return@map htmlTextsAreEqual(text, htmlText.text()!!)
                }
                .all { it }
        }.withLocalhostPagesOn(PORT)
    )
}

private const val PORT = 25555

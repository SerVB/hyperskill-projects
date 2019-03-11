package test

import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase
import solution.Solution45

// TODO: Solution6 is needed
class WebCrawlerStage6Test : SwingTest<WebCrawlerClue>(Solution45()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        val urlText = ComponentRequirements("UrlTextField", isEnabled = true) { window.textBox(it) }
        val getButton = ComponentRequirements("RunButton", isEnabled = true) { window.button(it) }
        val saveButton = ComponentRequirements("ExportButton", isEnabled = true) { window.button(it) }
        val pathToFileText = ComponentRequirements("ExportUrlTextField", isEnabled = true) { window.textBox(it) }

        val depthText = ComponentRequirements("DepthTextField", isEnabled = true) { window.textBox(it) }
        val depthCheckBox = ComponentRequirements("DepthCheckBox", isEnabled = true) { window.checkBox(it) }
        val parsedLabel = ComponentRequirements("ParsedLabel", isEnabled = true) { window.label(it) }

        return frameTests(::frame) +
                existenceTests(urlText, getButton, saveButton, pathToFileText, depthText, depthCheckBox, parsedLabel) +
                componentsAreEnabledTests(
                    urlText,
                    getButton,
                    saveButton,
                    pathToFileText,
                    depthText,
                    depthCheckBox,
                    parsedLabel
                ) +
                stage5Tests(
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText,
                    saveButtonRequirements = saveButton,
                    savePathTextFieldRequirements = pathToFileText,
                    depthTextFieldRequirements = depthText,
                    depthCheckBoxRequirements = depthCheckBox,
                    parsedLabelRequirements = parsedLabel,
                    depth = 1
                ) +
                stage5Tests(
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText,
                    saveButtonRequirements = saveButton,
                    savePathTextFieldRequirements = pathToFileText,
                    depthTextFieldRequirements = depthText,
                    depthCheckBoxRequirements = depthCheckBox,
                    parsedLabelRequirements = parsedLabel,
                    depth = 2
                ) +
                stage5Tests(
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText,
                    saveButtonRequirements = saveButton,
                    savePathTextFieldRequirements = pathToFileText,
                    depthTextFieldRequirements = depthText,
                    depthCheckBoxRequirements = depthCheckBox,
                    parsedLabelRequirements = parsedLabel,
                    depth = 100500
                )
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

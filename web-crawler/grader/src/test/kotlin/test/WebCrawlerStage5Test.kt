package test

import org.assertj.swing.fixture.JButtonFixture
import org.assertj.swing.fixture.JCheckBoxFixture
import org.assertj.swing.fixture.JLabelFixture
import org.assertj.swing.fixture.JTextComponentFixture
import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.CheckResult
import org.hyperskill.hstest.dev.testcase.TestCase
import solution.Solution45
import java.io.File

class WebCrawlerStage5Test : SwingTest<WebCrawlerClue>(Solution45()) {

    override fun generateTestCases(): List<TestCase<WebCrawlerClue>> {
        val titlesTable = ComponentRequirements("TitlesTable", isEnabled = false) { window.table(it) }
        val urlText = ComponentRequirements("UrlTextField", isEnabled = true) { window.textBox(it) }
        val getButton = ComponentRequirements("RunButton", isEnabled = true) { window.button(it) }
        val titleLabel = ComponentRequirements("TitleLabel", isEnabled = true) { window.label(it) }
        val saveButton = ComponentRequirements("ExportButton", isEnabled = true) { window.button(it) }
        val pathToFileText = ComponentRequirements("ExportUrlTextField", isEnabled = true) { window.textBox(it) }

        return frameTests(::frame) +
                existenceTests(titlesTable, urlText, getButton, titleLabel, saveButton, pathToFileText) +
                componentsAreEnabledTests(titlesTable, urlText, getButton, titleLabel, saveButton, pathToFileText) +
                stage3Tests(
                    titleLabelRequirements = titleLabel,
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText
                ) +
                stage4Tests(
                    titlesTableRequirements = titlesTable,
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText
                ) +
                stage5Tests(
                    getTextButtonRequirements = getButton,
                    locationTextFieldRequirements = urlText,
                    saveButtonRequirements = saveButton,
                    savePathTextFieldRequirements = pathToFileText,
                    depth = 1
                )
    }

    override fun check(reply: String, clue: WebCrawlerClue): CheckResult {
        return checkWebCrawlerTest(reply = reply, clue = clue)
    }
}

fun stage5Tests(
    saveButtonRequirements: ComponentRequirements<JButtonFixture>,
    getTextButtonRequirements: ComponentRequirements<JButtonFixture>,
    locationTextFieldRequirements: ComponentRequirements<JTextComponentFixture>,
    savePathTextFieldRequirements: ComponentRequirements<JTextComponentFixture>,
    depthTextFieldRequirements: ComponentRequirements<JTextComponentFixture>? = null,
    depthCheckBoxRequirements: ComponentRequirements<JCheckBoxFixture>? = null,
    parsedLabelRequirements: ComponentRequirements<JLabelFixture>? = null,
    depth: Int
): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest {
            val locationTextField = locationTextFieldRequirements.requireExistingComponent()
            val getTextButton = getTextButtonRequirements.requireExistingComponent()
            val saveButton = saveButtonRequirements.requireExistingComponent()
            val savePathTextField = savePathTextFieldRequirements.requireExistingComponent()

            val depthTextField = depthTextFieldRequirements?.requireExistingComponent()
            val depthCheckBox = depthCheckBoxRequirements?.requireExistingComponent()
            val parsedLabel = parsedLabelRequirements?.requireExistingComponent()

            for (url in pages.keys) {
                depthTextField?.setText("$depth")
                depthCheckBox?.enable()

                locationTextField.setText(url)

                getTextButton.click()

                val fileName = File("").absolutePath + "/temp.log"

                savePathTextField.setText(fileName)

                saveButton.click()

                val file = File(fileName)

                if (!file.exists()) {
                    return@createWebCrawlerTest fail("Your app doesn't create a file")
                }

                val contents = file.readText().lines().chunked(2).filter { it.size == 2 }
                val deepUrls = url.deepUrls(depth)

                if (contents.size != deepUrls.size) {
                    return@createWebCrawlerTest fail("File your app saves has a wrong lines number")
                }

                if (contents.map { it.first() }.toSet() != deepUrls) {
                    return@createWebCrawlerTest fail("File your app saves has a wrong child url")
                }

                val parsedCount = parsedLabel?.text()?.toIntOrNull()

                if (parsedCount != null && parsedCount != deepUrls.size) {
                    return@createWebCrawlerTest fail("Parsed pages number your app shows is wrong")
                }

                for ((writtenUrl, writtenTitle) in contents) {
                    if (pages.getValue(writtenUrl).title != writtenTitle) {
                        return@createWebCrawlerTest fail("File your app saves has a wrong pair of lines")
                    }
                }
            }

            return@createWebCrawlerTest CheckResult(true)
        }.withLocalhostPagesOn(PORT)
    )
}

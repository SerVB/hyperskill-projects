package test

import org.assertj.swing.fixture.JButtonFixture
import org.assertj.swing.fixture.JLabelFixture
import org.assertj.swing.fixture.JToggleButtonFixture
import org.hyperskill.hstest.v3.common.Utils
import org.hyperskill.hstest.v3.stage.SwingTest
import org.hyperskill.hstest.v3.testcase.CheckResult
import org.hyperskill.hstest.v3.testcase.TestCase
import solution.Solution56

class GameOfLifeStage6Test : SwingTest<ClueWithChecker>(Solution56()) {

    override fun generateTestCases(): List<TestCase<ClueWithChecker>> {
        val generationLabel = ComponentRequirements("GenerationLabel", isEnabled = true) { window.label(it) }
        val aliveLabel = ComponentRequirements("AliveLabel", isEnabled = true) { window.label(it) }

        val playButton = ComponentRequirements("PlayToggleButton", isEnabled = true) { window.toggleButton(it) }
        val resetButton = ComponentRequirements("ResetButton", isEnabled = true) { window.button(it) }

        return frameTests(::frame) +
                existenceTests(generationLabel, aliveLabel, playButton, resetButton) +
                componentsAreEnabledTests(generationLabel, aliveLabel, playButton, resetButton) +
                stage6Tests(
                    generationLabelRequirements = generationLabel,
                    aliveLabelRequirements = aliveLabel,
                    playButtonRequirements = playButton,
                    resetButtonRequirements = resetButton
                )
    }

    override fun check(reply: String, clue: ClueWithChecker): CheckResult {
        return checkClueWithCheckerTest(reply = reply, clue = clue)
    }
}

fun stage6Tests(
    generationLabelRequirements: ComponentRequirements<JLabelFixture>,
    aliveLabelRequirements: ComponentRequirements<JLabelFixture>,
    playButtonRequirements: ComponentRequirements<JToggleButtonFixture>,
    resetButtonRequirements: ComponentRequirements<JButtonFixture>
): List<TestCase<ClueWithChecker>> {
    return listOf(
        createPredefinedFeedbackTest("App crashes when reset is clicked") {
            val resetButton = resetButtonRequirements.requireExistingComponent()

            resetButton.click()

            return@createPredefinedFeedbackTest true
        },

        createDynamicFeedbackTest {
            /*
            Flip-flop play button, check labels, reset, flip-flop again, check again
            */

            val playButton = playButtonRequirements.requireExistingComponent()

            playButton.check()
            Utils.sleep(100)
            playButton.uncheck()

            with(checkLabelForInteger(generationLabelRequirements)) {
                if (!this.isCorrect) {
                    return@createDynamicFeedbackTest this
                }
            }
            with(checkLabelForInteger(aliveLabelRequirements)) {
                if (!this.isCorrect) {
                    return@createDynamicFeedbackTest this
                }
            }

            val resetButton = resetButtonRequirements.requireExistingComponent()
            resetButton.click()

            Utils.sleep(100)

            playButton.check()
            Utils.sleep(100)
            playButton.uncheck()

            with(checkLabelForInteger(generationLabelRequirements)) {
                if (!this.isCorrect) {
                    return@createDynamicFeedbackTest this
                }
            }
            with(checkLabelForInteger(aliveLabelRequirements)) {
                if (!this.isCorrect) {
                    return@createDynamicFeedbackTest this
                }
            }

            return@createDynamicFeedbackTest CheckResult.TRUE
        }
    )
}

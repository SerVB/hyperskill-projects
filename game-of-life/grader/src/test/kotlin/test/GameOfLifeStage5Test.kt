package test

import org.assertj.swing.fixture.JLabelFixture
import org.hyperskill.hstest.v3.stage.SwingTest
import org.hyperskill.hstest.v3.testcase.CheckResult
import org.hyperskill.hstest.v3.testcase.TestCase
import solution.Solution56

class GameOfLifeStage5Test : SwingTest<ClueWithChecker>(Solution56()) {

    override fun generateTestCases(): List<TestCase<ClueWithChecker>> {
        val generationLabel = ComponentRequirements("GenerationLabel", isEnabled = true) { window.label(it) }
        val aliveLabel = ComponentRequirements("AliveLabel", isEnabled = true) { window.label(it) }

        return frameTests(::frame) +
                existenceTests(generationLabel, aliveLabel) +
                componentsAreEnabledTests(generationLabel, aliveLabel) +
                stage5Tests(
                    generationLabelRequirements = generationLabel,
                    aliveLabelRequirements = aliveLabel
                )
    }

    override fun check(reply: String, clue: ClueWithChecker): CheckResult {
        return checkClueWithCheckerTest(reply = reply, clue = clue)
    }
}

fun stage5Tests(
    generationLabelRequirements: ComponentRequirements<JLabelFixture>,
    aliveLabelRequirements: ComponentRequirements<JLabelFixture>
): List<TestCase<ClueWithChecker>> {
    return listOf(
        createDynamicFeedbackTest {
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

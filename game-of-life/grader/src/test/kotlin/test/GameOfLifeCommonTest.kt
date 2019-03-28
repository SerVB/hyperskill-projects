package test

import org.assertj.swing.fixture.JLabelFixture
import org.hyperskill.hstest.dev.testcase.CheckResult

fun checkLabelForInteger(labelRequirements: ComponentRequirements<JLabelFixture>): CheckResult {
    val label = labelRequirements.requireExistingComponent()

    val labelDigits = label.text().trim { !it.isDigit() }

    if (labelDigits.toIntOrNull() == null) {
        return fail("The '${labelRequirements.name}' label doesn't contain an integer.")
    }

    return CheckResult.TRUE
}

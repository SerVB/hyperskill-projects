package test

import org.assertj.swing.fixture.AbstractJComponentFixture
import org.hyperskill.hstest.dev.stage.SwingTest
import org.hyperskill.hstest.dev.testcase.TestCase
import javax.swing.JFrame

fun frameTests(frameGetter: () -> JFrame): List<TestCase<WebCrawlerClue>> {
    return listOf(
        createWebCrawlerTest("Window is not visible") { frameGetter().isVisible },
        createWebCrawlerTest("Window title is empty") { frameGetter().title.isNotEmpty() }
    )
}

class ComponentRequirements<ComponentType : AbstractJComponentFixture<*, *, *>>(
    val name: String,
    val isEnabled: Boolean,
    private val componentFinder: (String) -> ComponentType?
) {
    val suitableComponent: ComponentType? by lazy { componentFinder(name) }
}

fun existenceTests(vararg components: ComponentRequirements<*>): List<TestCase<WebCrawlerClue>> {
    fun generateExistenceTest(requirements: ComponentRequirements<*>): TestCase<WebCrawlerClue> {
        return createWebCrawlerTest("No suitableComponent '${requirements.name}' found") {
            SwingTest.checkExistence { requirements.suitableComponent }
        }
    }

    return components.map(::generateExistenceTest)
}

fun componentsAreEnabledTests(vararg components: ComponentRequirements<*>): List<TestCase<WebCrawlerClue>> {
    fun generateIsEnabledTest(requirements: ComponentRequirements<*>): TestCase<WebCrawlerClue> {

        return if (requirements.isEnabled) {
            createWebCrawlerTest("'${requirements.name}' should be enabled") {
                val component = requireNotNull(requirements.suitableComponent) {
                    "Should check for the component existence before"
                }

                return@createWebCrawlerTest component.isEnabled
            }
        } else {
            createWebCrawlerTest("'${requirements.name}' should be disabled") {
                val component = requireNotNull(requirements.suitableComponent) {
                    "Should check for the component existence before"
                }

                return@createWebCrawlerTest !(component.isEnabled)
            }
        }
    }

    return components.map(::generateIsEnabledTest)
}

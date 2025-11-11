package opensavvy.sentier.core

import opensavvy.prepared.runner.testballoon.preparedSuite
import kotlin.random.Random

private val logger = Sentier.loggerFor("test")

context(_: TaskScope)
fun simple() {
	logger.info { "This seems to work!" }
	logger.warn { "This is a warning." }

	val n = logger.task(
		description = { "Generate a random number" },
	) {
		logger.debugTask(
			description = { "Wait for some time, please" },
		) {

		}

		Random.nextInt()
	}
}

val ScenarioTest by preparedSuite {
	test("Simple example") {
		with(TaskScope(Task.Root)) {
			simple()
		}
	}
}

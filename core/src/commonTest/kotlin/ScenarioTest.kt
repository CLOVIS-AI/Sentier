package opensavvy.sentier.core

import kotlin.random.Random

private val logger = Sentier.loggerFor("test")

fun TaskScope.test() {
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

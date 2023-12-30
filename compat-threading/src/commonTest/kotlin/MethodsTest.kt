package opensavvy.sentier.threads

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import opensavvy.prepared.suite.SuiteDsl
import opensavvy.sentier.core.Sentier

fun SuiteDsl.methodsTests() = suite("High-level") {
	val logger= Sentier.loggerFor(Unit)

	test("Log events at specific levels") {
		logger.trace { "A trace" }
		logger.debug { "A debug message" }
		logger.info { "An informational level" }
		logger.warn { "A warning" }
		logger.error { "An error message" }
		logger.fatal { "A fatal error message" }
	}

	test("The task builder creates a child task") {
		val topLevelTask = currentTask()

		logger.task({ "A child task" }) {
			currentTask() shouldNotBe topLevelTask
		}
	}

	test("The task builder pops the child task it created") {
		val topLevelTask = currentTask()

		logger.task({ "A child task" }) {}

		currentTask() shouldBe topLevelTask
	}

	test("The task builder pops the child task even if an exception is thrown") {
		val topLevelTask = currentTask()

		shouldThrow<IllegalAccessException> {
			logger.task({ "A child task" }) {
				throw IllegalAccessException()
			}
		}

		currentTask() shouldBe topLevelTask
	}
}

package opensavvy.sentier.core

/**
 * Main entrypoint to Sentier.
 *
 * This class is used to configure the [logLevel], [register][registerSync] new loggers, and [instantiate][loggerFor] loggers.
 */
object Sentier {

	/**
	 * Configures the minimal logging level.
	 *
	 * Each [registered][registerSync] logger can have its own additional check for log level.
	 * However, if an event is logged at a lower level than this variable, none of the loggers will be notified.
	 *
	 * By default, all events are captured (set to [LogLevel.Trace]).
	 */
	var logLevel: LogLevel = LogLevel.Trace

	internal val loggers = LinkedHashSet<Logger>()

	/**
	 * Registers a synchronous [Logger] implementation to be notified whenever any event with a higher level than [logLevel] happens.
	 *
	 * If [logger] has already been registered, this function does nothing.
	 *
	 * **Warning.** This function is not thread-safe. It is the caller's responsibility to ensure no two loggers are registered
	 * at the same time.
	 */
	fun registerSync(logger: Logger) {
		loggers += logger
	}

	/**
	 * Instantiates a [Logger] implementation for a given [owner].
	 *
	 * ### Example
	 *
	 * ```kotlin
	 * class Foo {
	 *     private val logger = Sentier.loggerFor(this)
	 * }
	 * ```
	 *
	 * When generating a logger for top-level functions, name it using a [String]:
	 * ```kotlin
	 * private val logger = Sentier.loggerFor("my.package.name.FooKt")
	 * ```
	 */
	fun loggerFor(owner: Any): Logger =
		SentierLogger(owner)
}

private class SentierLogger(
	val owner: Any,
) : Logger {
	override fun isEnabled(level: LogLevel): Boolean =
		level >= Sentier.logLevel

	override fun forceLog(description: String, task: Task, level: LogLevel) {
		Sentier.loggers.forEach {
			if (it.isEnabled(level))
				it.forceLog(description, task, level)
		}
	}

	override fun forceStart(description: String, task: Task, level: LogLevel) {
		require(task != Task.Root) { "Sentier.forceStart should only be called with a child task, but the root task was received: $task" }
		Sentier.loggers.forEach {
			if (it.isEnabled(level))
				it.forceStart(description, task, level)
		}
	}

	override fun forceEnd(result: Any?, task: Task, outcome: TaskOutcome, level: LogLevel) {
		require(task != Task.Root) { "Sentier.forceEnd should only be called with a child task, but the root task was received: $task" }
		Sentier.loggers.forEach {
			if (it.isEnabled(level))
				it.forceEnd(result, task, outcome, level)
		}
	}
}

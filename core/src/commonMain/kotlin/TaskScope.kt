package opensavvy.sentier.core

/**
 * Lightweight wrapper to transmit the [currentTask] through function call hierarchies.
 *
 * ### Example
 *
 * ```kotlin
 * private val logger = Sentier.loggerFor("test")
 *
 * context(TaskScope)
 * fun foo() {
 *     logger.info { "This is an information message." }
 *
 *     logger.task(
 *         description = { "Accessing remote information." }
 *     ) {
 *         logger.trace { "Sending a database request…" }
 *     }
 * }
 * ```
 */
@JvmInline
value class TaskScope(val currentTask: Task) {

	inline fun Logger.log(level: LogLevel = LogLevel.Info, description: () -> String) {
		if (isEnabled(level))
			forceLog(description(), currentTask, level)
	}

	inline fun Logger.trace(description: () -> String) = log(LogLevel.Trace, description)
	inline fun Logger.debug(description: () -> String) = log(LogLevel.Debug, description)
	inline fun Logger.info(description: () -> String) = log(LogLevel.Info, description)
	inline fun Logger.warn(description: () -> String) = log(LogLevel.Warning, description)
	inline fun Logger.error(description: () -> String) = log(LogLevel.Error, description)
	inline fun Logger.fatal(description: () -> String) = log(LogLevel.Error, description)

	inline fun <T : Any?> Logger.task(
		description: () -> String,
		level: LogLevel = LogLevel.Info,
		decideOutcome: (T) -> TaskOutcome = { TaskOutcome.Success },
		block: TaskScope.() -> T,
	): T =
		if (isEnabled(level)) {
			val task = currentTask.createChild()
			forceStart(description(), task, level)
			try {
				val result = block()
				forceEnd(result, task, outcome = decideOutcome(result), level)
				result
			} catch (e: Throwable) {
				forceEnd(e, task, outcome = TaskOutcome.Exception, level)
				throw e
			}
		} else {
			block()
		}

	inline fun <T : Any?> Logger.traceTask(
		description: () -> String,
		decideOutcome: (T) -> TaskOutcome = { TaskOutcome.Success },
		block: TaskScope.() -> T,
	): T =
		task(description, LogLevel.Trace, decideOutcome, block)

	inline fun <T : Any?> Logger.debugTask(
		description: () -> String,
		decideOutcome: (T) -> TaskOutcome = { TaskOutcome.Success },
		block: TaskScope.() -> T,
	): T =
		task(description, LogLevel.Debug, decideOutcome, block)

}

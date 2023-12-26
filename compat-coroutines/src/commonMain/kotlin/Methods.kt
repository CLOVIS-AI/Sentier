package opensavvy.sentier.coroutines

import kotlinx.coroutines.withContext
import opensavvy.sentier.core.LogLevel
import opensavvy.sentier.core.Logger
import opensavvy.sentier.core.TaskOutcome

suspend inline fun Logger.trace(description: () -> String) = withCurrentTask { trace(description) }
suspend inline fun Logger.debug(description: () -> String) = withCurrentTask { debug(description) }
suspend inline fun Logger.info(description: () -> String) = withCurrentTask { info(description) }
suspend inline fun Logger.warn(description: () -> String) = withCurrentTask { warn(description) }
suspend inline fun Logger.error(description: () -> String) = withCurrentTask { error(description) }
suspend inline fun Logger.fatal(description: () -> String) = withCurrentTask { fatal(description) }

suspend inline fun <T : Any?> Logger.task(
	description: () -> String,
	level: LogLevel = LogLevel.Info,
	decideOutcome: (T) -> TaskOutcome = { TaskOutcome.Success },
	crossinline block: () -> T,
): T = withCurrentTask {
	task(description, level, decideOutcome) {
		val child = this.currentTask
		withContext(child.asCoroutineContext()) {
			block()
		}
	}
}

suspend inline fun <T : Any?> Logger.traceTask(
	description: () -> String,
	decideOutcome: (T) -> TaskOutcome,
	crossinline block: () -> T,
): T = task(description, LogLevel.Trace, decideOutcome, block)

suspend inline fun <T : Any?> Logger.debugTask(
	description: () -> String,
	decideOutcome: (T) -> TaskOutcome,
	crossinline block: () -> T,
): T = task(description, LogLevel.Debug, decideOutcome, block)

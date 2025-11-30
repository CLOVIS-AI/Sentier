package opensavvy.sentier.coroutines

import kotlinx.coroutines.withContext
import opensavvy.sentier.core.LogLevel
import opensavvy.sentier.core.Logger
import opensavvy.sentier.core.TaskOutcome
import opensavvy.sentier.core.TaskScope
import opensavvy.sentier.core.debug
import opensavvy.sentier.core.error
import opensavvy.sentier.core.fatal
import opensavvy.sentier.core.info
import opensavvy.sentier.core.task
import opensavvy.sentier.core.trace
import opensavvy.sentier.core.warn

suspend inline fun Logger.trace(description: () -> String) = withCurrentTask { this.trace(description) }
suspend inline fun Logger.debug(description: () -> String) = withCurrentTask { this.debug(description) }
suspend inline fun Logger.info(description: () -> String) = withCurrentTask { this.info(description) }
suspend inline fun Logger.warn(description: () -> String) = withCurrentTask { this.warn(description) }
suspend inline fun Logger.error(description: () -> String) = withCurrentTask { this.error(description) }
suspend inline fun Logger.fatal(description: () -> String) = withCurrentTask { this.fatal(description) }

suspend inline fun <T : Any?> Logger.task(
	description: () -> String,
	level: LogLevel = LogLevel.Info,
	decideOutcome: (T) -> TaskOutcome = { TaskOutcome.Success },
	crossinline block: () -> T,
): T = withCurrentTask {
	this.task(description, level, decideOutcome) {
		val child = contextOf<TaskScope>().currentTask
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

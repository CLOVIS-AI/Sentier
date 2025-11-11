package opensavvy.sentier.threads

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

inline fun Logger.trace(description: () -> String) = withCurrentTask { this.trace(description) }
inline fun Logger.debug(description: () -> String) = withCurrentTask { this.debug(description) }
inline fun Logger.info(description: () -> String) = withCurrentTask { this.info(description) }
inline fun Logger.warn(description: () -> String) = withCurrentTask { this.warn(description) }
inline fun Logger.error(description: () -> String) = withCurrentTask { this.error(description) }
inline fun Logger.fatal(description: () -> String) = withCurrentTask { this.fatal(description) }

@OptIn(DelicateTaskApi::class)
inline fun <T : Any?> Logger.task(
	description: () -> String,
	level: LogLevel = LogLevel.Info,
	decideOutcome: (T) -> TaskOutcome = { TaskOutcome.Success },
	crossinline block: () -> T,
): T = withCurrentTask {
	task(description, level, decideOutcome) {
		val child = contextOf<TaskScope>().currentTask

		try {
			pushCurrentTask(child.id)
			block()
		} finally {
			popCurrentTask(child.id)
		}
	}
}

inline fun <T : Any?> Logger.traceTask(
	description: () -> String,
	decideOutcome: (T) -> TaskOutcome,
	crossinline block: () -> T,
): T = task(description, LogLevel.Trace, decideOutcome, block)

inline fun <T : Any?> Logger.debugTask(
	description: () -> String,
	decideOutcome: (T) -> TaskOutcome,
	crossinline block: () -> T,
): T = task(description, LogLevel.Debug, decideOutcome, block)

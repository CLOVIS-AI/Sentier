package opensavvy.sentier.threads

import opensavvy.sentier.core.LogLevel
import opensavvy.sentier.core.Logger
import opensavvy.sentier.core.TaskOutcome

inline fun Logger.trace(description: () -> String) = withCurrentTask { trace(description) }
inline fun Logger.debug(description: () -> String) = withCurrentTask { debug(description) }
inline fun Logger.info(description: () -> String) = withCurrentTask { info(description) }
inline fun Logger.warn(description: () -> String) = withCurrentTask { warn(description) }
inline fun Logger.error(description: () -> String) = withCurrentTask { error(description) }
inline fun Logger.fatal(description: () -> String) = withCurrentTask { fatal(description) }

@OptIn(DelicateTaskApi::class)
inline fun <T : Any?> Logger.task(
	description: () -> String,
	level: LogLevel = LogLevel.Info,
	decideOutcome: (T) -> TaskOutcome = { TaskOutcome.Success },
	crossinline block: () -> T,
): T = withCurrentTask {
	task(description, level, decideOutcome) {
		val child = this.currentTask

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

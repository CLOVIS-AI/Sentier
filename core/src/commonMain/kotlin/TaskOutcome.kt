package opensavvy.sentier.core

/**
 * Possible results of a task.
 */
enum class TaskOutcome {
	Success,
	LogicalFailure,
	Exception,
	Cancelled,
}

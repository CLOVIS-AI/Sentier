package opensavvy.sentier.core

/**
 * Synchronous logger API.
 *
 * Each logger must [expose][isEnabled] which log levels are enabled for it.
 */
interface Logger {

	/**
	 * Advertises whether [level] is enabled for this logger or not.
	 *
	 * This function is used by
	 *
	 * @return `true` if [level] is enabled, `false` otherwise.
	 */
	fun isEnabled(level: LogLevel): Boolean

	/**
	 * Creates a new atomic event with the given [description] for [task].
	 *
	 * An atomic event adds information to an existing [task]. It doesn't itself generate a new child task.
	 * To create non-atomic events, see [forceStart].
	 *
	 * It is the caller's responsibility to check whether [level] is [enabled][isEnabled], before calling this function.
	 */
	fun forceLog(
		description: String,
		task: Task,
		level: LogLevel,
	)

	/**
	 * Creates a new non-atomic event with the given [description].
	 *
	 * The passed [task] must be a newly-created [child][Task.createChild] of the current task.
	 * After this function is called, the [task] exists and can be used to report other events.
	 * Do not forget to call [forceEnd] to close this task.
	 *
	 * It is the caller's responsibility to check whether [level] is [enabled][isEnabled], before calling this function.
	 */
	fun forceStart(
		description: String,
		task: Task,
		level: LogLevel,
	)

	/**
	 * Ends a non-atomic event with the given [result].
	 *
	 * The passed [task] must be a task previously started using [forceStart].
	 * The passed [level] must be the same as was passed to [forceStart].
	 *
	 * It is the caller's responsibility to check whether [level] is [enabled][isEnabled], before calling this function.
	 */
	fun forceEnd(
		result: Any?,
		task: Task,
		outcome: TaskOutcome,
		level: LogLevel,
	)

}

package opensavvy.sentier.core

/**
 * A logging event that can have children events.
 *
 * ### Naming
 *
 * In traditional logging frameworks, "events" are atomic descriptions of the current state of the system.
 * Sentier differentiates between "events", which mean the same thing, and "tasks", which are non-atomic: both events
 * and tasks are linked to a parent task, forming a hierarchy of the entire system behavior, and allowing tracing
 * of events through system boundaries.
 */
class Task internal constructor(
	/** Identifier of this task. */
	val id: TaskId,

	/**
	 * Identifier of this task's parent task.
	 *
	 * `null` if this task is the [Root] task.
	 */
	val parent: TaskId?,
) {

	init {
		require(id != parent) { "A task cannot have the same ID as its parent, but found id $id for parent $parent" }

		if (parent == null)
			require(id == TaskId.Root) { "Only the root task is allowed to have 'null' as a parent. The root task must have the ID TaskId.Root (${TaskId.Root}), but found: $id" }
		else
			require(id != TaskId.Root) { "Only the root task is allowed to use the TaskId.Root identifier, but found: $id" }
	}

	/**
	 * Creates a child of the current task.
	 */
	fun createChild(): Task =
		Task(TaskId(), parent = id)

	// region Equality

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Task) return false

		if (id != other.id) return false
		if (parent != other.parent) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id.hashCode()
		result = 31 * result + (parent?.hashCode() ?: 0)
		return result
	}

	// endregion

	companion object {
		/**
		 * The root task.
		 *
		 * The root task has a `null` parent, and has the ID [TaskId.Root].
		 */
		val Root = Task(TaskId.Root, null)

		/**
		 * Instantiates a [Task] for a given [id].
		 *
		 * Use this function to generate a [Task] object for a task created across a system boundary (e.g. a client-side task).
		 */
		fun remote(id: String) = Task(TaskId(id), TaskId.Root)
	}
}

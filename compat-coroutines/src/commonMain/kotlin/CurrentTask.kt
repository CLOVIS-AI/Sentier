package opensavvy.sentier.coroutines

import opensavvy.sentier.core.Task
import opensavvy.sentier.core.TaskScope
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * Stores the current task into the [CoroutineContext] of a coroutine.
 */
class CurrentTask(
	val task: Task,
) : AbstractCoroutineContextElement(Companion) {

	companion object : CoroutineContext.Key<CurrentTask>
}

suspend fun currentTaskOrNull() =
	coroutineContext[CurrentTask]?.task

suspend fun currentTask() =
	currentTaskOrNull()
		?: error("This coroutine does not have an assigned task. For more information, see opensavvy.sentier.coroutines.asCoroutineContext.")

suspend inline fun <T> withCurrentTask(block: TaskScope.() -> T) =
	with(TaskScope(currentTask()), block)

fun Task.asCoroutineContext() =
	CurrentTask(this)

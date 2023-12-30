package opensavvy.sentier.threads

import opensavvy.sentier.core.Task
import opensavvy.sentier.core.TaskId
import opensavvy.sentier.core.withChild

private val taskStack = ThreadLocal.withInitial {
	ArrayList<Task>()
		.apply { add(Task.Root) }
}

actual fun currentTask(): Task {
	return taskStack.get().last()
}

actual fun pushCurrentTask(child: TaskId) {
	require(child != TaskId.Root) { "pushCurrentTask($child) was called, but it is forbidden to push the root task." }

	val new = taskStack.get().last().withChild(child)
	taskStack.get().add(new)
}

actual fun popCurrentTask(child: TaskId) {
	require(child != TaskId.Root) { "popCurrentTask($child) was called, but it is forbidden to pop the root task." }

	val last = taskStack.get().last()

	// Verification: the last task should have the same ID
	check(last.id == child) {
		buildString {
			appendLine("popCurrentTask($child) was called, but the current task is ${last.id}.")
			appendLine("Current task stack (root first, the last task is the only one that can be popped):")
			var parent: TaskId? = null
			for (task in taskStack.get()) {
				append(" - ${task.id}")

				if (task.parent != parent)
					append(" INCOHERENT SITUATION: THIS TASK'S PARENT DOES NOT MATCH THE PREVIOUS TASK. Expected $parent, but this task's parent is ${task.parent}.")

				if (task.id == child)
					append(" <-- Requested task.")

				appendLine()
				parent = task.id
			}
		}
	}

	taskStack.get().removeLast()
}

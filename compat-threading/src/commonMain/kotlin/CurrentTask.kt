package opensavvy.sentier.threads

import opensavvy.sentier.core.Task
import opensavvy.sentier.core.TaskId

/**
 * Accesses the current [Task].
 */
expect fun currentTask(): Task

/**
 * Pushes [child] as a child of the [currentTask].
 *
 * This is a low-level method to work with tasks. Unless you are writing some kind of library based on this project,
 * you should use [task] instead.
 *
 * Pushing the [root task][Task.Root] is forbidden and throws an [IllegalArgumentException].
 */
@DelicateTaskApi
expect fun pushCurrentTask(child: TaskId)

/**
 * Pops the [currentTask], checking that it is [child].
 *
 * This method forces the caller to provide what it thinks is the task that should be popped to check task ordering
 * bugs early (for example if a subroutine has pushed a task but not popped it). In those cases, this function
 * will throw an [IllegalStateException] with a detailed error report on the current task stack.
 *
 * This is a low-level method to work with tasks. Unless you are writing some kind of library based on this project,
 * you should use [task] instead.
 *
 * Popping the [root task][Task.Root] is forbidden and throws an [IllegalArgumentException].
 */
@DelicateTaskApi
expect fun popCurrentTask(child: TaskId)

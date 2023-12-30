package opensavvy.sentier.threads

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.withContext
import opensavvy.prepared.suite.SuiteDsl
import opensavvy.prepared.suite.prepared
import opensavvy.sentier.core.Task
import opensavvy.sentier.core.TaskId

fun SuiteDsl.currentTaskTests() = suite("Low-level") {
	val randomTaskId by prepared { TaskId() }
	val thread1 by preparedThread("thread1")
	val thread2 by preparedThread("thread2")

	test("The default current task is the root task") {
		withContext(thread1()) {
			currentTask() shouldBe Task.Root
		}
	}

	test("Pushing a task only impacts the current thread") {
		withContext(thread1()) {
			pushCurrentTask(randomTaskId())
		}

		withContext(thread2()) {
			currentTask().id shouldNotBe randomTaskId()
		}
	}

	val depths = listOf(0, 1, 27)
	for (depth in depths) suite("Depth $depth") {
		fun applyDepth() {
			repeat(depth) {
				pushCurrentTask(TaskId())
			}
		}

		test("Pushing a task changes the current task") {
			withContext(thread1()) {
				applyDepth()

				pushCurrentTask(randomTaskId())
				currentTask().id shouldBe randomTaskId()
			}
		}

		test("Popping the task brings back the previous task into scope") {
			withContext(thread1()) {
				applyDepth()

				val current = currentTask()
				pushCurrentTask(randomTaskId())
				popCurrentTask(randomTaskId())
				currentTask() shouldBe current
			}
		}

		test("Cannot push the root task") {
			withContext(thread1()) {
				applyDepth()

				shouldThrow<IllegalArgumentException> {
					pushCurrentTask(TaskId.Root)
				}
			}
		}

		test("Cannot pop the root task") {
			withContext(thread1()) {
				applyDepth()

				shouldThrow<IllegalArgumentException> {
					popCurrentTask(TaskId.Root)
				}
			}
		}

		test("Cannot pop a task that is not in the stack") {
			withContext(thread1()) {
				applyDepth()
				pushCurrentTask(randomTaskId())

				shouldThrow<IllegalStateException> {
					popCurrentTask(TaskId())
				}
			}
		}
	}

	test("Cannot pop a task that is not the last one") {
		withContext(thread1()) {
			repeat(3) { pushCurrentTask(TaskId()) }
			val previous = currentTask()
			pushCurrentTask(TaskId())

			shouldThrow<IllegalStateException> {
				popCurrentTask(previous.id)
			}
		}
	}

	test("Cannot pop the root") {
		withContext(thread1()) {
			shouldThrow<IllegalStateException> {
				popCurrentTask(TaskId())
			}
		}
	}
}

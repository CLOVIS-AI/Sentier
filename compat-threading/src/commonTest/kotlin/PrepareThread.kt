package opensavvy.sentier.threads

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import opensavvy.prepared.suite.cleanUp
import opensavvy.prepared.suite.prepared

/**
 * Creates a thread pool with [count] threads.
 */
@OptIn(DelicateCoroutinesApi::class)
fun preparedThreads(name: String, count: Int) = prepared {
	newFixedThreadPoolContext(count, name)
		.also { cleanUp("Free $name") { it.close() } }
}

/**
 * Creates a single thread.
 */
fun preparedThread(name: String) = preparedThreads(name, count = 1)

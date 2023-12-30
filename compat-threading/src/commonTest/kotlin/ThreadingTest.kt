package opensavvy.sentier.threads

import io.kotest.core.spec.style.StringSpec
import opensavvy.prepared.runner.kotest.preparedSuite

class ThreadingTest : StringSpec({
	preparedSuite {
		currentTaskTests()
		methodsTests()
	}
})

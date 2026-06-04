package opensavvy.sentier.threads

import opensavvy.prepared.runner.testballoon.preparedSuite

val ThreadingTest by preparedSuite {
	currentTaskTests()
	methodsTests()
}

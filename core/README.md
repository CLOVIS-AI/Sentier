# Module Sentier

Multiplatform observability facade.

<a href="https://search.maven.org/search?q=dev.opensavvy.sentier.core"><img src="https://img.shields.io/maven-central/v/dev.opensavvy.sentier/core.svg?label=Maven%20Central"></a>

## Configuration

By default, Sentier doesn't capture any log events. In the application entry-point, start by [registering a logger implementation][opensavvy.sentier.core.Sentier.registerSync]. Compatibility for other popular logging/observability tools is provided in other modules of this repository.
```kotlin
fun main() {
	Sentier.registerSync(/* … */)
	
	// Rest of the code
}
```

Optionally, also configure the [log level][opensavvy.sentier.core.Sentier.logLevel].  

## Usage

Start by [instantiating a logger instance][opensavvy.sentier.core.Sentier.loggerFor]:
```kotlin
class Foo {
	private val logger = Sentier.loggerFor(this)
}
```

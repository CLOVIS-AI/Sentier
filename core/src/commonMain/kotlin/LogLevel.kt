package opensavvy.sentier.core

enum class LogLevel { // order of elements is important! from least important to most important
	/** Completely detailed system events, used to gain a complete understanding (e.g. content of database requests). */
	Trace,
	/** Fine-grained information about what the system is doing. */
	Debug,
	/** Overall explanation of what the system is doing. */
	Info,
	/** Something strange happens that should be investigated, but it is not necessarily an error. */
	Warning,
	/** Something went wrong, but the system is not at risk of failing because of it. */
	Error,
	/** Something went wrong, putting the entire system at risk of failing. */
	Fatal,
	;
}

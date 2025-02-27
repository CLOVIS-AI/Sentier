@file:OptIn(ExperimentalUuidApi::class)

package opensavvy.sentier.core

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// The UUID library is external to us. We don't know if it will have breaking changes in the future.
// We keep it entirely as an implementation detail in case it changes in the future.

/**
 * Unique identifier of a [Task].
 */
class TaskId internal constructor(
	internal val id: Uuid,
) {

	constructor() : this(Uuid.random())

	constructor(id: String) : this(Uuid.parse(id))

	override fun toString() =
		id.toString()

	// region Equality

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is TaskId) return false

		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}

	// endregion

	companion object {
		val Root = TaskId(Uuid.NIL)
	}
}

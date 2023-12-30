package opensavvy.sentier.threads

/**
 * Marks low-level functions of the local task management.
 *
 * Marked functions are particularly easy to misuse, so their documentation should be read carefully before attempting to use them.
 * They are intended to make integration into frameworks possible, not for regular application-level use.
 *
 * This opt-in interface does not mark a stability risk (source nor binary).
 */
@RequiresOptIn("This function is particularly easy to misuse. Ensure you have read its documentation entirely before attempting to use it.")
@MustBeDocumented
annotation class DelicateTaskApi

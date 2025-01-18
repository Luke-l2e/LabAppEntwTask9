package de.hhn.labappentwtask9.data

/**
 * Enum representing the state of a task.
 *
 * It provides three possible states for a task:
 * - [ACTIVE]: Represents tasks that are currently active and in progress.
 * - [DONE]: Represents tasks that have been completed.
 * - [BOTH]: Represents a combined state, including both active and completed tasks.
 */
enum class TaskState {
    ACTIVE, DONE, BOTH
}
package de.hhn.labappentwtask9.database.data

import java.time.LocalDateTime

/**
 * Represents a task with its associated details.
 *
 * @property id The unique identifier for the task. Defaults to 0 for new tasks.
 * @property name The name or title of the task.
 * @property description A detailed description of the task.
 * @property endDate The deadline or completion date and time for the task.
 * @property state The state of the task:
 * - `false` for active tasks.
 * - `true` for completed tasks.
 * @property priority The priority level assigned to the task.
 */
data class Task(
    val id: Int = 0,
    var name: String,
    var description: String,
    var endDate: LocalDateTime,
    var state: Boolean = false,
    var priority: Priority
)

package de.hhn.labappentwtask9.database.controller

import android.content.ContentValues
import android.content.Context
import android.util.Log
import de.hhn.labappentwtask9.data.TaskState
import de.hhn.labappentwtask9.database.DbHelper
import de.hhn.labappentwtask9.database.data.Priority
import de.hhn.labappentwtask9.database.data.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Controller class for managing tasks in the database.
 *
 * @param context The context used for database operations.
 */
class TaskController(context: Context) {
    private val dbHelper = DbHelper(context)

    /**
     * Inserts a new task into the database.
     *
     * @param task The task to be inserted.
     * @return `true` if the insertion was successful, `false` otherwise.
     */
    fun insertTask(task: Task): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", task.name)
                put("description", task.description)
                put("priority", task.priority.id)
                put(
                    "enddate",
                    task.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                put("state", task.state)
            }
            val result = db.insert("tasks", null, values)
            result != -1L
        } catch (e: Exception) {
            Log.e("TaskController", "Insert failed", e)
            false
        } finally {
            db.close()
        }
    }

    /**
     * Updates an existing task in the database.
     *
     * @param task The task to be updated.
     * @return `true` if the update was successful, `false` otherwise.
     */
    fun updateTask(task: Task): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", task.name)
                put("description", task.description)
                put("priority", task.priority.id)
                put(
                    "enddate",
                    task.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                put("state", task.state)
            }
            val result = db.update("tasks", values, "id = ?", arrayOf(task.id.toString()))
            Log.d("TaskController", "Update result: $result, Task ID: ${task.id}")
            result > 0
        } catch (e: Exception) {
            Log.e("TaskController", "Update failed", e)
            false
        } finally {
            db.close()
        }
    }

    /**
     * Deletes a task from the database.
     *
     * @param taskId The ID of the task to be deleted.
     * @return `true` if the deletion was successful, `false` otherwise.
     */
    fun deleteTask(taskId: Int): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            val result = db.delete("tasks", "id = ?", arrayOf(taskId.toString()))
            result > 0
        } catch (e: Exception) {
            Log.e("TaskController", "Delete failed", e)
            false
        } finally {
            db.close()
        }
    }

    /**
     * Retrieves a list of tasks from the database.
     *
     * @param tasksToRetrieve The state of tasks to retrieve: [TaskState.ACTIVE], [TaskState.DONE], or [TaskState.BOTH].
     * @return A list of tasks matching the specified state.
     */
    fun getTasks(tasksToRetrieve: TaskState = TaskState.BOTH): List<Task> {
        val priorities = getAllPriorities()
        val db = dbHelper.readableDatabase
        val tasks = mutableListOf<Task>()
        val sqlCommand =
            if (tasksToRetrieve == TaskState.BOTH) "SELECT * FROM tasks" else "SELECT * FROM tasks WHERE state = ?"
        val state = if (tasksToRetrieve == TaskState.ACTIVE) "0" else "1"
        val cursor = db.rawQuery(sqlCommand, arrayOf(state))
        try {
            if (cursor.moveToFirst()) {
                do {
                    val priorityId = cursor.getInt(cursor.getColumnIndexOrThrow("priority"))

                    val priority = priorities[priorityId]
                        ?: throw IllegalStateException(
                            "Invalid priority ID: $priorityId for task ID: ${
                                cursor.getInt(
                                    cursor.getColumnIndexOrThrow("id")
                                )
                            }"
                        )

                    val task = Task(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        priority = priority,
                        endDate = LocalDateTime.parse(
                            cursor.getString(cursor.getColumnIndexOrThrow("enddate")),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        ),
                        state = cursor.getInt(cursor.getColumnIndexOrThrow("state")) == 1
                    )
                    tasks.add(task)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("TaskController", "Fetching tasks failed", e)
        } finally {
            cursor.close()
            db.close()
        }
        return tasks
    }

    /**
     * Retrieves all priorities from the database.
     *
     * @return A map where the key is the priority ID and the value is the corresponding [Priority] object.
     */
    fun getAllPriorities(): HashMap<Int, Priority> {
        val db = dbHelper.readableDatabase
        val priorities = HashMap<Int, Priority>()
        val cursor = db.rawQuery("SELECT * FROM priorities", null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val priority = Priority(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        level = cursor.getString(cursor.getColumnIndexOrThrow("level")),
                    )
                    priorities[priority.id] = priority
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("TaskController", "Fetching priorities failed", e)
        } finally {
            cursor.close()
            db.close()
        }
        return priorities
    }
}
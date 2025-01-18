package de.hhn.labappentwtask9.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.hhn.labappentwtask9.R
import de.hhn.labappentwtask9.data.TaskState
import de.hhn.labappentwtask9.database.controller.TaskController
import de.hhn.labappentwtask9.database.data.Priority
import de.hhn.labappentwtask9.database.data.Task
import de.hhn.labappentwtask9.ui.components.EditTaskDialog
import de.hhn.labappentwtask9.ui.components.ExpandableTaskCard

/**
 * A composable function that displays a list of tasks based on the specified task state (e.g., active or finished).
 * The screen includes:
 * - A top app bar with the screen's title and a back button.
 * - A floating action button that allows the user to create a new task.
 * - A list of tasks, where each task is displayed in an expandable card that allows editing.
 * - A dialog for editing or deleting tasks.
 *
 * @param context The context of the current screen, used to interact with the task controller.
 * @param navController The navigation controller used for navigating back to the dashboard.
 * @param title The title of the screen, displayed in the top app bar.
 * @param stateOfTasksToShow The state of tasks to display (e.g., active tasks or finished tasks).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    context: Context,
    navController: NavHostController,
    title: String,
    stateOfTasksToShow: TaskState
) {
    val taskController = TaskController(context)
    var tasks by remember { mutableStateOf(taskController.getTasks(stateOfTasksToShow)) }
    val priorities = remember { taskController.getAllPriorities() }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                selectedTask = null
                showEditDialog = true
            },
            content = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.round_add_24),
                    contentDescription = stringResource(R.string.create_task)
                )
            }
        )
    }, topBar = {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_to_dashboard)
                    )
                }
            }
        )

    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                ExpandableTaskCard(
                    task = task,
                    onEditClick = {
                        selectedTask = task
                        showEditDialog = true
                    }
                )
            }
        }
    }


    if (showEditDialog) {
        EditTaskDialog(context,
            task = selectedTask,
            availablePriorities = priorities,
            onDismiss = { showEditDialog = false },
            onSave = { task ->
                if (task.id == 0) {
                    taskController.insertTask(task)
                } else {
                    taskController.updateTask(task)
                }
                tasks = taskController.getTasks(stateOfTasksToShow)
                showEditDialog = false
            },
            onDelete = { task ->
                taskController.deleteTask(task.id)
                tasks = taskController.getTasks(stateOfTasksToShow)
                showEditDialog = false
            }
        )
    }
}
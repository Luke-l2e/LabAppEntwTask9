package de.hhn.labappentwtask9.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.hhn.labappentwtask9.R
import de.hhn.labappentwtask9.database.data.Priority
import de.hhn.labappentwtask9.database.data.Task
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * A composable function that displays a dialog for creating or editing a task.
 *
 * @param context The context used to display toast messages and access resources.
 * @param task The task to edit. If `null`, a new task will be created.
 * @param onDismiss A callback function invoked when the dialog is dismissed.
 * @param onSave A callback function invoked when the user saves the task.
 * It provides the updated or new task.
 * @param onDelete A callback function invoked when the user deletes the task.
 * Only available if a task is being edited.
 * @param availablePriorities A map of available priorities, where the key is the priority ID
 * and the value is the `Priority` object.
 */
@Composable
fun EditTaskDialog(
    context: Context,
    task: Task?,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: (Task) -> Unit,
    availablePriorities: HashMap<Int, Priority>
) {
    var name by remember { mutableStateOf(task?.name ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var priority by remember {
        mutableStateOf(
            task?.priority ?: availablePriorities.values.first()
        )
    }
    var endDate by remember {
        mutableStateOf(
            task?.endDate ?: LocalDateTime.now().withSecond(0).withNano(0)
        )
    }
    var state by remember { mutableStateOf(task?.state ?: false) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (task == null) stringResource(R.string.create_new_task) else stringResource(
                    R.string.edit_task
                )
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${stringResource(R.string.priority)}:")
                    PrioritiesDropdownMenu(
                        priority = priority,
                        onPrioritySelected = { selectedPriority ->
                            priority = selectedPriority
                        },
                        availablePriorities = availablePriorities,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val formatter =
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
                            .withZone(ZoneId.systemDefault()).withLocale(
                                Locale.getDefault()
                            )
                    Text(text = "${stringResource(R.string.due_date)}:")

                    Text(
                        text = endDate.format(formatter),
                        modifier = Modifier.clickable { showDatePicker = true })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${stringResource(R.string.finished)}:")
                    Checkbox(checked = state, onCheckedChange = { state = it })
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isBlank() || description.isBlank()) {
                    Toast.makeText(context, R.string.error_blank_input, Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val updatedTask = Task(
                    id = task?.id ?: 0,
                    name = name,
                    description = description,
                    priority = priority,
                    state = state,
                    endDate = endDate
                )
                onSave(updatedTask)
            }) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            if (task != null) {
                Button(onClick = { onDelete(task) }) {
                    Text(stringResource(R.string.delete))
                }
            }
        }
    )

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { selectedDateAsLong ->
                if (selectedDateAsLong == null) {
                    showDatePicker = false
                    return@DatePickerModal
                }
                val selectedDate = Instant.ofEpochMilli(selectedDateAsLong)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                endDate = endDate.withYear(selectedDate.year).withDayOfYear(selectedDate.dayOfYear)
                showTimePicker = true
            },
            onDismiss = { showDatePicker = false })
    }

    if (showTimePicker) {
        TimePickerModal(onTimeSelected = { selectedTime ->
            endDate =
                endDate.withHour(selectedTime.hour).withMinute(selectedTime.minute).withSecond(0)
                    .withNano(0)
        }, onDismiss = { showTimePicker = false })
    }
}


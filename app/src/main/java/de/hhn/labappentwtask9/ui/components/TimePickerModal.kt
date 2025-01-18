package de.hhn.labappentwtask9.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import de.hhn.labappentwtask9.R
import java.time.LocalTime

/**
 * A composable function that displays a modal dialog with a time picker to select a time.
 *
 * @param onTimeSelected A callback function invoked when the user selects a time from the picker.
 *                       It receives the selected time as a [LocalTime].
 * @param onDismiss A callback function invoked when the dialog is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState()

    TimePickerDialog(
        onCancel = { onDismiss() },
        onConfirm = {
            val selectedTime: LocalTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
            onTimeSelected(selectedTime)
            onDismiss()
        }
    ) {
        TimePicker(state = timePickerState)
    }
}

/**
 * A composable function that displays a custom time picker dialog.
 *
 * @param title The title of the dialog, displayed at the top.
 * @param onCancel A callback function invoked when the user cancels the selection.
 * @param onConfirm A callback function invoked when the user confirms their time selection.
 * @param content The content to be displayed inside the dialog, typically the time picker UI.
 */
@Composable
private fun TimePickerDialog(
    title: String = stringResource(R.string.select_time),
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text(stringResource(R.string.cancel)) }
                    TextButton(
                        onClick = onConfirm
                    ) { Text(stringResource(R.string.ok)) }
                }
            }
        }
    }
}
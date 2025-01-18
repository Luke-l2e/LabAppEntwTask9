package de.hhn.labappentwtask9.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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

/**
 * A composable function that displays a dropdown menu for selecting a priority from the available list.
 *
 * @param priority The currently selected priority, which is displayed at the top of the dropdown.
 * @param onPrioritySelected A callback function invoked when a priority is selected from the dropdown.
 * @param availablePriorities A map of available priorities where the key is the priority ID and
 *                            the value is the corresponding priority object.
 */
@Composable
fun PrioritiesDropdownMenu(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    availablePriorities: HashMap<Int, Priority>
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${priority.id} - ${priority.level}")
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) stringResource(R.string.collapse) else stringResource(
                    R.string.expand
                )
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availablePriorities.forEach { priority ->
                DropdownMenuItem(
                    text = { Text("${priority.value.id} - ${priority.value.level}") },
                    onClick = {
                        onPrioritySelected(priority.value)
                        expanded = false
                    }
                )
            }
        }
    }
}
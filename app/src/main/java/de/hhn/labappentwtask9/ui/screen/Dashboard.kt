package de.hhn.labappentwtask9.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.hhn.labappentwtask9.R
import de.hhn.labappentwtask9.data.TaskState

/**
 * A composable function that represents the main dashboard screen.
 * It serves as the starting point for navigating between different screens:
 * - Active tasks
 * - Finished tasks
 *
 * The screen contains two buttons that allow the user to navigate to the active or finished tasks screen.
 * The top app bar displays the title "Dashboard".
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            Scaffold(topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.dashboard),
                            style = MaterialTheme.typography.displayMedium,
                            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                        )
                    }
                )

            }) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { navController.navigate("active_tasks_screen") }) {
                        Text(
                            stringResource(R.string.active_tasks),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("finished_tasks_screen") }) {
                        Text(
                            stringResource(R.string.finished_tasks),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

        }
        composable("active_tasks_screen") {
            val context = LocalContext.current
            TaskListScreen(
                context,
                navController,
                stringResource(R.string.active_tasks),
                TaskState.ACTIVE
            )
        }
        composable("finished_tasks_screen") {
            val context = LocalContext.current
            TaskListScreen(
                context,
                navController,
                stringResource(R.string.finished_tasks),
                TaskState.DONE
            )
        }
    }
}
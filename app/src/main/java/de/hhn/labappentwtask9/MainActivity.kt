package de.hhn.labappentwtask9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import de.hhn.labappentwtask9.ui.screen.Dashboard

/**
 * The main activity of the application.
 *
 * This activity serves as the entry point of the app and sets up the UI theme, navigation host,
 * and the main content. It uses Jetpack Compose to define the UI components and applies the
 * application's theme.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dashboard()
        }
    }
}
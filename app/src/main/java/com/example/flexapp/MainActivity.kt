/**
 * The main class which launches the app,
 * its colors, fonts and themes are defined in the ui.theme package,
 *
 * the database implementation of the app is defined in the database package
 *
 * the screens in the app are defined in the screens package, and their
 * ViewModels are defined in the viewmodels subpackage
 *
 * the navigation between the screens is defined in the navigation package
 * */

package com.example.flexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.flexapp.ui.theme.FlexAppTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlexAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WorkoutApp()
                }
            }
        }
    }
}



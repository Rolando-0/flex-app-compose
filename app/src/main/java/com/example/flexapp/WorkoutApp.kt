/** A kotlin file containing the main component, called
 * 'WorkOutApp'
 *
 * which wraps the entire front end logic of the app,
 * such as the routing between screens and bottom navigation bar, used
 * by the main activity in MainActivity.kt
 *
 * */


package com.example.flexapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flexapp.navigation.BottomNavBar
import com.example.flexapp.navigation.WorkOutNavHost


@Composable
fun WorkoutApp(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ){innerPadding ->
        WorkOutNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }

}

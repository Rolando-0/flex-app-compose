package com.example.bottomnavigationpractice

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigationpractice.navigation.BottomNavBar
import com.example.bottomnavigationpractice.navigation.WorkOutNavHost


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

package com.example.bottomnavigationpractice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavigationpractice.screens.FaceScreen
import com.example.bottomnavigationpractice.screens.AboutScreen
import com.example.bottomnavigationpractice.screens.WorkoutScreen


@Composable
fun WorkOutNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    ){
    NavHost(
        navController = navController,
        startDestination = "Workout",
        modifier = modifier
    ){
        composable(route = "Workout"){
            WorkoutScreen()
        }
        composable(route = "About"){
            AboutScreen()
        }
        composable(route = "Cal. Calc"){
            FaceScreen()
        }
    }

}
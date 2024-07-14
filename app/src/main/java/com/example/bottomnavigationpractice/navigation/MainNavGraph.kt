package com.example.bottomnavigationpractice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.screens.FaceScreen
import com.example.bottomnavigationpractice.screens.AboutScreen
import com.example.bottomnavigationpractice.screens.RoutineDetailsScreen
import com.example.bottomnavigationpractice.screens.RoutineEntryScreen
import com.example.bottomnavigationpractice.screens.RoutinesScreen
import kotlinx.serialization.Serializable

@Serializable
object RoutinesRoute

@Serializable
object RoutineEntryRoute
@Serializable
data class RoutineDetailsRoute(
   val routineId: Long
)

@Serializable
object AboutRoute

@Serializable
object CalcRoute



@Composable
fun WorkOutNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    ){
    NavHost(
        navController = navController,
        startDestination = RoutinesRoute,
        modifier = modifier
    ){
        composable<RoutinesRoute>{
            RoutinesScreen(
                navigateToRoutineEntry = {navController.navigate(RoutineEntryRoute) },
                navigateToRoutineDetails = {navController.navigate(it)}
            )
        }
        composable<RoutineEntryRoute>{
           RoutineEntryScreen(
               navigateUp = {navController.navigateUp()}
           )
        }
        composable<RoutineDetailsRoute>{
//            val args = it.toRoute<RoutineDetailsRoute>()
//            RoutineDetailsScreen(
//                args.routineId,
//
//                )
        }
        composable<AboutRoute>{
            AboutScreen()
        }
        composable<CalcRoute>{
            FaceScreen()
        }


    }

}


package com.example.bottomnavigationpractice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.screens.FaceScreen
import com.example.bottomnavigationpractice.screens.AboutScreen
import com.example.bottomnavigationpractice.screens.ExerciseEntryScreen
import com.example.bottomnavigationpractice.screens.ExerciseScreen
import com.example.bottomnavigationpractice.screens.RoutineDetailsScreen
import com.example.bottomnavigationpractice.screens.RoutineEntryScreen
import com.example.bottomnavigationpractice.screens.RoutinesScreen
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
object RoutinesRoute

@Serializable
object RoutineEntryRoute
@Serializable
data class RoutineDetailsRoute(
   val routineId: Long,
   val routineName: String,
   val routineDesc: String

)

@Serializable
data class ExerciseEntryRoute(
    val routineId: Long
)
@Serializable
data class ExerciseRoute(
    val exerciseId: Long,
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
                navigateToRoutineDetails =
                {id,name,desc ->
                    navController.navigate(RoutineDetailsRoute(routineId = id, routineName = name, routineDesc = desc))
                }
            )
        }
        composable<RoutineEntryRoute>{
           RoutineEntryScreen(
               navigateBack = {navController.navigateUp()}
           )
        }
        composable<RoutineDetailsRoute>{
            val args = it.toRoute<RoutineDetailsRoute>()
           RoutineDetailsScreen(
               routineId = args.routineId,
               routineName = args.routineName,
               routineDesc = args.routineDesc,
               navigateBack = {navController.navigateUp()},
               navigateToExerciseEntry = { navController.navigate(ExerciseEntryRoute(routineId = args.routineId))},
               navigateToExercise = {exerciseId -> navController.navigate(ExerciseRoute(exerciseId, routineId = args.routineId))}
           )
        }
        composable<ExerciseEntryRoute>{
            val args = it.toRoute<ExerciseEntryRoute>()
            ExerciseEntryScreen (
                routineId = args.routineId,
                navigateBack = {navController.navigateUp()}
                )

        }
        composable<ExerciseRoute> {
            val args =  it.toRoute<ExerciseRoute>()
            ExerciseScreen(
                exerciseId = args.exerciseId,
                navigateBack = {navController.navigateUp()}
            )
        }
        composable<AboutRoute>{
            AboutScreen()
        }
        composable<CalcRoute>{
            FaceScreen()
        }


    }

}


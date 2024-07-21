/**
 * A kotlin class file containing
 * WorkOutNavHost,
 *
 * critical component of the app which controls the routing
 * and arguments passed between screens.
 *
 * Contains an object for each reachable screen via a route in the app
 * notated with a @Serializable object or data class, with data classes
 * having arguments.
 *
 * Uses the new type-safe navigation feature in androidx.navigation
 * version 2.8.0-beta05, to allow for an easier way to pass arguments
 * and eliminate boiler plate code
 *
 *

 *
 * */


package com.example.bottomnavigationpractice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.data.ProgressItem
import com.example.bottomnavigationpractice.screens.FaceScreen
import com.example.bottomnavigationpractice.screens.AboutScreen
import com.example.bottomnavigationpractice.screens.ExerciseEntryScreen
import com.example.bottomnavigationpractice.screens.ExerciseScreen
import com.example.bottomnavigationpractice.screens.ProgressItemScreen
import com.example.bottomnavigationpractice.screens.ProgressScreen
import com.example.bottomnavigationpractice.screens.RoutineDetailsScreen
import com.example.bottomnavigationpractice.screens.RoutineEditScreen
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
data class RoutineEditRoute(
    val routineId: Long,
    val routineName: String
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
object ProgressRoute

@Serializable
data class ProgressItemRoute(
    val progressItemId: Long,
    val itemName: String,
    val valueType: String
)


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
               navigateToExercise = {exerciseId -> navController.navigate(ExerciseRoute(exerciseId, routineId = args.routineId))},
               navigateToEditRoutine = {navController.navigate(RoutineEditRoute(args.routineId,args.routineName))}
           )
        }
        composable<RoutineEditRoute>{
            val args = it.toRoute<RoutineEditRoute>()
            RoutineEditScreen(
                args.routineId,
                args.routineName,
                navigateBack = {navController.navigateUp()}
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
        composable<ProgressRoute>{
            ProgressScreen(
                navigateToProgressItem = {id,name,valueType ->
                    navController.navigate(ProgressItemRoute(progressItemId = id, itemName = name, valueType = valueType))
                                         },
            )
        }

        composable<ProgressItemRoute> {
            val args = it.toRoute<ProgressItemRoute>()
            ProgressItemScreen(
                navigateBack = {navController.navigateUp()},
                progressItemId = args.progressItemId,
                progressItemName = args.itemName,
                progressValueType = args.valueType

            )
        }
        composable<CalcRoute>{
            FaceScreen()
        }


    }

}


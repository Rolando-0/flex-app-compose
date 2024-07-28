/**
 * A viewModel object that contains the state
 * to be used the Exercise Screen in ExerciseScreen.kt,
 * allowing user input to update the state of UI components that
 * have been edited by the user.
 *
 * Receives a savedStateHandle object, which is used to receive arguments
 * from the screen that navigated to the Exercise Screen,
 * which is most likely the Routine Details Screen in RoutineDetailsScreen.kt
 *
 * Receives a RoutineRepository object from RoutineRepository.kt, used to make database queries
 *
 * Initialized by a Factory object in AppViewModelProvider.kt
 *
 * */


package com.example.flexapp.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.flexapp.database.entities.Exercise
import com.example.flexapp.database.RoutineRepository
import com.example.flexapp.navigation.ExerciseRoute
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ExerciseViewModel (
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository
): ViewModel() {

    private val exerciseId = savedStateHandle.toRoute<ExerciseRoute>().exerciseId // Argument: an exerciseId, representing the primary key of this exercise
    private val routineId = savedStateHandle.toRoute<ExerciseRoute>().routineId // Argument: an routineId, representing which routine this exercise will belong to
    var exerciseUiState by mutableStateOf(ExerciseUiState())
        private set

    init{
      viewModelScope.launch {
          exerciseUiState = routineRepository.getExerciseById(exerciseId)
              .filterNotNull()
              .first()
              .toExerciseUiState()
      }
    }
    fun updateExerciseUiState(exerciseDetails: ExerciseDetails){
        exerciseUiState = ExerciseUiState(
            exerciseDetails = exerciseDetails,
            isEditValid = validateInput(exerciseDetails)
        )
    }

    suspend fun updateExercise(){
        if(validateInput()){
            routineRepository.updateExercise(
                Exercise(
                    exerciseId = exerciseId,
                    name = exerciseUiState.exerciseDetails.name,
                    reps = exerciseUiState.exerciseDetails.reps,
                    sets = exerciseUiState.exerciseDetails.sets,
                    youtubeUrl = exerciseUiState.exerciseDetails.youtubeUrl,
                    routineId = routineId
                )
            )
        }
    }

    private fun validateInput(entryUiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean{
        return with(entryUiState){
            name.isNotBlank() && reps.isNotBlank() && sets.isNotBlank() && youtubeUrl.isNotBlank()
        }
    }
    suspend fun deleteExercise(){
        routineRepository.deleteExerciseById(exerciseId)
    }

}

data class ExerciseUiState(
    val exerciseDetails: ExerciseDetails = ExerciseDetails(),
    val isEditValid: Boolean = false
)

fun Exercise.toExerciseUiState(isEditValid: Boolean = false): ExerciseUiState = ExerciseUiState(
    exerciseDetails = this.toExerciseDetails(),
    isEditValid = isEditValid
)
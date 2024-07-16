package com.example.bottomnavigationpractice.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.data.Exercise
import com.example.bottomnavigationpractice.data.RoutineRepository
import com.example.bottomnavigationpractice.navigation.ExerciseRoute
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ExerciseViewModel (
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository
): ViewModel() {

    private val exerciseId = savedStateHandle.toRoute<ExerciseRoute>().exerciseId
    private val routineId = savedStateHandle.toRoute<ExerciseRoute>().routineId
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
            routineRepository.insertExercise(exerciseUiState.exerciseDetails.toExercise(routineId),routineId )
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
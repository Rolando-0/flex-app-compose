package com.example.bottomnavigationpractice.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.data.Exercise
import com.example.bottomnavigationpractice.data.RoutineRepository
import com.example.bottomnavigationpractice.navigation.ExerciseEntryRoute
import com.example.bottomnavigationpractice.navigation.RoutineDetailsRoute

class ExerciseEntryViewModel(
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository,
): ViewModel() {

    private val routineId = savedStateHandle.toRoute<ExerciseEntryRoute>().routineId

    var exerciseEntryUiState by mutableStateOf(ExerciseEntryUiState())
        private set

    fun updateExerciseEntryState(exerciseDetails: ExerciseDetails){
        exerciseEntryUiState = ExerciseEntryUiState(
            exerciseDetails = exerciseDetails,
            isEntryValid = validateInput(exerciseDetails)
        )
    }

    suspend fun saveExercise(){
        if(validateInput()){
            routineRepository.insertExercise(exerciseEntryUiState.exerciseDetails.toExercise(routineId), routineId)
        }
    }

    private fun validateInput(entryUiState: ExerciseDetails = exerciseEntryUiState.exerciseDetails): Boolean{
        return with(entryUiState){
            name.isNotBlank() && reps.isNotBlank() && sets.isNotBlank() && youtubeUrl.isNotBlank()
        }
    }

}

data class ExerciseEntryUiState(
    val exerciseDetails: ExerciseDetails = ExerciseDetails(),
    val isEntryValid: Boolean = false
)

data class ExerciseDetails(
    val exerciseId: Int = 0,
    val name: String = "",
    val reps: String = "",
    val sets: String = "",
    val youtubeUrl: String = ""
)

fun ExerciseDetails.toExercise(routineId: Long): Exercise = Exercise(
    name = name,
    reps = reps,
    sets = sets,
    youtubeUrl = youtubeUrl,
    routineId = routineId
)

fun Exercise.toExerciseEntryUiState(isEntryValid: Boolean = false): ExerciseEntryUiState = ExerciseEntryUiState(
    exerciseDetails = this.toExerciseDetails(),
    isEntryValid = isEntryValid
)


fun Exercise.toExerciseDetails(): ExerciseDetails = ExerciseDetails(
    name = name,
    reps = reps,
    sets = sets,
    youtubeUrl = youtubeUrl,
)

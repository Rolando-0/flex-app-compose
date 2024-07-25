/**
 * A viewModel object that contains the state
 * to be used the Exercise Entry Screen in ExerciseEntryScreen.kt,
 * allowing user input to update the state of UI components that
 * display what the user has typed in the fields.
 *
 * Receives a savedStateHandle object, which is used to receive arguments
 * from the screen that navigated to the Exercise Entry Screen,
 * which is most likely the Routine Details Screen in RoutineDetailsScreen.kt
 *
 * Receives a RoutineRepository object from RoutineRepository.kt, used to make database queries
 *
 * Initialized by a Factory object in AppViewModelProvider.kt
 *
 * */

package com.example.bottomnavigationpractice.screens.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.data.Exercise
import com.example.bottomnavigationpractice.data.LibraryExercise
import com.example.bottomnavigationpractice.data.RoutineRepository
import com.example.bottomnavigationpractice.navigation.ExerciseEntryRoute
import com.example.bottomnavigationpractice.navigation.RoutineDetailsRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream

class ExerciseEntryViewModel(
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository,
): ViewModel() {
    /*
    Entry form state
    * */
    private val routineId =
        savedStateHandle.toRoute<ExerciseEntryRoute>().routineId // Argument: an routineId, representing which routine this exercise will belong to

    var exerciseEntryUiState by mutableStateOf(ExerciseEntryUiState())
        private set

    fun updateExerciseEntryState(exerciseDetails: ExerciseDetails) {
        exerciseEntryUiState = ExerciseEntryUiState(
            exerciseDetails = exerciseDetails,
            isEntryValid = validateInput(exerciseDetails)
        )
    }

    suspend fun saveExercise() {
        if (validateInput()) {
            routineRepository.insertExercise(
                exerciseEntryUiState.exerciseDetails.toExercise(
                    routineId
                ), routineId
            )
        }
    }

    private fun validateInput(entryUiState: ExerciseDetails = exerciseEntryUiState.exerciseDetails): Boolean {
        return with(entryUiState) {
            name.isNotBlank() && reps.isNotBlank() && sets.isNotBlank() && youtubeUrl.isNotBlank()
        }
    }

    /*
    Library exercise state
    */
    //Internal - used by the viewModel
    private val _libraryExercises = MutableStateFlow<List<LibraryExercise>>(emptyList())

    //External - simply observed (read-only) access used by the ui
    val libraryExercises: StateFlow<List<LibraryExercise>> = _libraryExercises


    val searchQuery = MutableStateFlow("")

    val searchedLibrary: StateFlow<List<LibraryExercise>> = combine(
        _libraryExercises,
        searchQuery
    ) { exercises, query ->
        if (query.isEmpty()) exercises else exercises.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Timeout of 5 seconds
        initialValue = emptyList()
    )

    fun loadExercises(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            // Offload heavy file reading to IO dispatcher
            val inputStream: InputStream = context.assets.open("exerciseLibrary.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val json = Json { ignoreUnknownKeys = true }
            val exercises = json.decodeFromString<List<LibraryExercise>>(jsonString)

            // Update state on the main thread
            withContext(Dispatchers.Main) {
                _libraryExercises.value = exercises
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
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

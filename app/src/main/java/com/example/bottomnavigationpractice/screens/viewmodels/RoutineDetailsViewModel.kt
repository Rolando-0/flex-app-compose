/**
 * A viewModel object that contains the state
 * to be used in the Routine Details Screen in RoutineDetailsScreen.kt,
 * allowing user input to update the state of UI components that
 * display the list of exercises.
 *
 * Receives a savedStateHandle object, which is used to receive arguments
 * from the screen that navigated to the Routine Details Screen,
 * which is most likely the Routine Screen in RoutineDetailsScreen.kt
 *
 * Receives a RoutineRepository object from RoutineRepository.kt, used to make database queries
 *
 * Initialized by a Factory object in AppViewModelProvider.kt
 *
 * */

package com.example.bottomnavigationpractice.screens.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.data.Exercise
import com.example.bottomnavigationpractice.data.RoutineRepository
import com.example.bottomnavigationpractice.navigation.RoutineDetailsRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RoutineDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository
): ViewModel() {

    private val routineId = savedStateHandle.toRoute<RoutineDetailsRoute>().routineId // Argument: a routineId, representing which routine will be queried for its list of exercises

    val routineDetailsUiState: StateFlow<RoutineDetailsUiState> =
        routineRepository.getExercisesForRoutine(routineId)
            .map{
                RoutineDetailsUiState(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RoutineDetailsUiState()
            )



    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class RoutineDetailsUiState(
    val exercises: List<Exercise> = emptyList()
)
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

    private val routineId = savedStateHandle.toRoute<RoutineDetailsRoute>().routineId

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
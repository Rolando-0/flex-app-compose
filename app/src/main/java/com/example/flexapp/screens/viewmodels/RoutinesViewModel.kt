/**
 * A viewModel object that contains the state
 * to be used the Routine Screen in RoutineScreen.kt,
 * allowing user input to update the state of UI components that
 * display what the list of routines
 *
 * Most likely navigated to by using the bottom navigation bar in BottomNavBar.kt
 *
 * Receives a RoutineRepository object from RoutineRepository.kt, used to make database queries
 *
 * Initialized by a Factory object in AppViewModelProvider.kt
 *
 * */




package com.example.flexapp.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexapp.database.entities.Routine
import com.example.flexapp.database.RoutineRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
/*
ViewModel for main routine screen
 */
class RoutinesViewModel (

    private val routineRepository: RoutineRepository
    ): ViewModel() {

    val routineUiState: StateFlow<RoutineUiState> =
        routineRepository.getAllRoutines().map { RoutineUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RoutineUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}




data class RoutineUiState(
    val routineList: List<Routine> = listOf()
)
package com.example.flexapp.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.flexapp.database.RoutineRepository
import com.example.flexapp.navigation.RoutineEditRoute

/**
 * A viewModel object that contains the state
 * to be used the Routine Edit Screen in RoutineEditScreen.kt,
 * allowing user input to update the state of UI components that
 * display what the user has typed in the fields.
 *
 * Receives a savedStateHandle object, which is used to receive arguments
 * from the screen that navigated to the Routine Edit Screen,
 * which is most likely the Routine Details Screen in RoutineDetailsScreen.kt, as it contains
 * the button to edit the specific routine
 *
 * Receives a RoutineRepository object from RoutineRepository.kt, used to make database queries
 *
 * Initialized by a Factory object in AppViewModelProvider.kt
 *
 * */




class RoutineEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository
): ViewModel() {

    private val routineId = savedStateHandle.toRoute<RoutineEditRoute>().routineId //Argument passed to this viewModel (See MainNavGraph.kt)
    var routineEditUiState by mutableStateOf(RoutineEntryUiState())
        private set

    fun updateRoutineEditState(routineDetails: RoutineDetails){
        routineEditUiState = RoutineEntryUiState(
            routineDetails = routineDetails,
            isEntryValid = validateInput(routineDetails)
        )
    }

    suspend fun updateRoutine(){
        if(validateInput()){
            val newName = routineEditUiState.routineDetails.name
            val newDesc = routineEditUiState.routineDetails.desc
            routineRepository.updateRoutine(routineId,newName,newDesc)
        }
    }

    private fun validateInput(entryUiState: RoutineDetails = routineEditUiState.routineDetails): Boolean{
        return with(entryUiState){
            name.isNotBlank() && desc.isNotBlank()
        }
    }
    suspend fun deleteRoutine(){
        routineRepository.deleteRoutineById(routineId)
    }


}
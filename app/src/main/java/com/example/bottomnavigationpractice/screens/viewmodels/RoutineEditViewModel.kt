package com.example.bottomnavigationpractice.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.data.RoutineRepository
import com.example.bottomnavigationpractice.navigation.RoutineDetailsRoute
import com.example.bottomnavigationpractice.navigation.RoutineEditRoute

class RoutineEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository
): ViewModel() {

    private val routineId = savedStateHandle.toRoute<RoutineEditRoute>().routineId
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
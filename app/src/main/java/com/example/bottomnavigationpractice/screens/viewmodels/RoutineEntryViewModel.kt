package com.example.bottomnavigationpractice.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bottomnavigationpractice.data.Routine
import com.example.bottomnavigationpractice.data.RoutineRepository

class RoutineEntryViewModel (
    private val routineRepository: RoutineRepository
    ): ViewModel() {

        var routineEntryUiState by mutableStateOf(RoutineEntryUiState())
            private set

        fun updateRoutineEntryState(routineDetails: RoutineDetails){
            routineEntryUiState = RoutineEntryUiState(
                routineDetails = routineDetails,
                isEntryValid = validateInput(routineDetails)
            )
        }

        suspend fun saveRoutine(){
            if(validateInput()){
                routineRepository.insertRoutine(routineEntryUiState.routineDetails.toRoutine())
            }
        }

        private fun validateInput(entryUiState: RoutineDetails = routineEntryUiState.routineDetails): Boolean{
            return with(entryUiState){
                name.isNotBlank() && desc.isNotBlank()
            }
        }

    }



data class RoutineEntryUiState(
    val routineDetails: RoutineDetails = RoutineDetails(),
    val isEntryValid: Boolean = false
)
data class RoutineDetails(
    val name: String ="",
    val desc: String =""
)

fun RoutineDetails.toRoutine(): Routine = Routine(
    name = name,
    desc = desc
)

fun Routine.toRoutineEntryUiState(isEntryValid: Boolean = false): RoutineEntryUiState = RoutineEntryUiState(
    routineDetails = this.toRoutineDetails(),
    isEntryValid = isEntryValid

)

fun Routine.toRoutineDetails(): RoutineDetails = RoutineDetails(
    name = name,
    desc = desc
)
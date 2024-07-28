package com.example.flexapp.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexapp.database.entities.ProgressItem
import com.example.flexapp.database.ProgressRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressViewModel(
    private val progressRepository: ProgressRepository
): ViewModel() {

    val progressUiState: StateFlow<ProgressUiState> =
        progressRepository.getAllProgressItems()
            .map { ProgressUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProgressUiState()
            )

    var progressEntryUiState by mutableStateOf(ProgressEntryUiState())
        private set

    fun updateProgressEntryState(progressDetails: ProgressDetails){
        progressEntryUiState = ProgressEntryUiState(
            progressDetails = progressDetails,
            isEntryValid = validateInput(progressDetails)
        )
    }

    suspend fun saveProgressItem(){
        if(validateInput()){
            progressRepository.insertProgressItem(progressEntryUiState.progressDetails.toProgressItem())
        }
    }

    suspend fun deleteProgressItem(progressItem: ProgressItem){
        progressRepository.deleteProgressItem(progressItem)
    }

    private fun validateInput(entryUiState: ProgressDetails = progressEntryUiState.progressDetails): Boolean{
        return with(entryUiState){
            name.isNotBlank() && valueType.isNotBlank()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ProgressUiState(
    val progressItemList: List<ProgressItem> = listOf()
)

data class ProgressEntryUiState(
    val progressDetails: ProgressDetails = ProgressDetails(),
    val isEntryValid: Boolean = false
)

data class ProgressDetails(
    val name: String = "",
    val valueType: String = ""
)

fun ProgressDetails.toProgressItem(): ProgressItem = ProgressItem(
    name = name,
    valueType = valueType
)

fun ProgressItem.toProgressEntryUiState(isEntryValid: Boolean = false): ProgressEntryUiState = ProgressEntryUiState(
    progressDetails = this.toProgressDetails(),
    isEntryValid = isEntryValid

)

fun ProgressItem.toProgressDetails(): ProgressDetails = ProgressDetails(
    name = name,
    valueType = valueType
)
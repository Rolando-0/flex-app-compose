package com.example.bottomnavigationpractice.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.bottomnavigationpractice.data.ProgressDataPoint
import com.example.bottomnavigationpractice.data.ProgressRepository
import com.example.bottomnavigationpractice.navigation.ProgressItemRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Locale

class ProgressDataViewModel(
    savedStateHandle: SavedStateHandle,
    private val progressRepository: ProgressRepository
): ViewModel()
{
    private val progressItemId = savedStateHandle.toRoute<ProgressItemRoute>().progressItemId

    val progressDataUiState: StateFlow<ProgressDataUiState> =
        progressRepository.getDataPointsByProgressItemId(progressItemId)
            .map{
                ProgressDataUiState(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProgressDataUiState()
            )



    var progressDataEntryUiState by mutableStateOf(ProgressDataEntryUiState())
        private set

    fun updateDataEntryState(progressDataPointDetails: ProgressDataDetails){
        progressDataEntryUiState = ProgressDataEntryUiState(
            progressDataDetails = progressDataPointDetails,
            isEntryValid = validateInput(progressDataPointDetails)
        )
    }

    suspend fun saveProgressDataPoint(){
        if(validateInput()){
            progressRepository.insertDataPoint(progressDataEntryUiState.progressDataDetails.toProgressDataItem(progressItemId))


        }
    }

    suspend fun deleteDataPoint(progressDataPoint: ProgressDataPoint){
        progressRepository.deleteDataPoint(progressDataPoint)
    }

    private fun validateInput(entryUiState: ProgressDataDetails = progressDataEntryUiState.progressDataDetails): Boolean{
        return with(entryUiState){

            value.isNotBlank() && (value.toIntOrNull() != null ) && dateString.isNotBlank() && isValidDateFormat(dateString)
        }
    }
    private fun isValidDateFormat(date: String): Boolean {

        val regex = Regex("""\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])""")
        // Check if the date matches has leading zeroes for month and day
        if(!regex.matches(date)){
            return false
        }
        //Parse for edge cases
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }



    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ProgressDataUiState(
    val progressDataPoints: List<ProgressDataPoint> = listOf()
)

data class ProgressDataEntryUiState(
    val progressDataDetails: ProgressDataDetails = ProgressDataDetails(),
    val isEntryValid: Boolean = false
)

data class ProgressDataDetails(
    val value: String = "",
    val dateString: String = "",
)

fun ProgressDataDetails.toProgressDataItem(progressItemId: Long): ProgressDataPoint = ProgressDataPoint(
    value = value.toIntOrNull() ?: 0,
    dateString = dateString,
    progressItemId = progressItemId
)

fun ProgressDataPoint.toProgressDataEntryUiState(isEntryValid: Boolean = false): ProgressDataEntryUiState = ProgressDataEntryUiState(
    progressDataDetails = this.toProgressDataDetails(),
    isEntryValid = isEntryValid

)

fun ProgressDataPoint.toProgressDataDetails(): ProgressDataDetails = ProgressDataDetails(
    value = value.toString(),
    dateString = dateString
)
/**
 * A Screen in the app itself, which can be arrived at from the Routine Details screen in
 * RoutineDetailsScreen.kt
 *
 * The screen itself is composed of a form where the user can type in the information of a new
 * exercise to insert in the currently accessed routine.
 *
 * Collects state and makes database queries
 * from a viewModel object in ExerciseEntryViewModel.kt
 *
 **/


package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bottomnavigationpractice.data.AppViewModelProvider
import com.example.bottomnavigationpractice.navigation.GoBackTopAppBar
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseDetails
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseEntryUiState
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseEntryViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEntryScreen(
    routineId: Long,
    navigateBack: () -> Unit,
    viewModel: ExerciseEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { GoBackTopAppBar(title = "Add new exercise", canNavigateBack = true, navigateUp = navigateBack)}
    ){innerPadding ->

        ExerciseEntryBody(
            exerciseEntryUiState = viewModel.exerciseEntryUiState,
            onFieldValueChange = viewModel::updateExerciseEntryState,
            onSaveClick = {
                coroutineScope.launch{
                    val url = viewModel.exerciseEntryUiState.exerciseDetails.youtubeUrl
                    val updatedDetails = viewModel.exerciseEntryUiState.exerciseDetails.copy(youtubeUrl = convertToEmbedUrl(url))
                    viewModel.updateExerciseEntryState(updatedDetails)
                    viewModel.saveExercise()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()


        )

    }
}
@Composable
fun ExerciseEntryBody(
    exerciseEntryUiState: ExerciseEntryUiState,
    onFieldValueChange: (ExerciseDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ExerciseInputForm(
            exerciseDetails = exerciseEntryUiState.exerciseDetails,
            onFieldValueChange = onFieldValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = exerciseEntryUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Composable
fun ExerciseInputForm(
    exerciseDetails: ExerciseDetails,
    onFieldValueChange: (ExerciseDetails) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        OutlinedTextField(
            value = exerciseDetails.name,
            onValueChange = { onFieldValueChange(exerciseDetails.copy(name = it)) },
            label = { Text("Exercise Name") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = exerciseDetails.reps,
            onValueChange = { onFieldValueChange(exerciseDetails.copy(reps = it)) },
            label = { Text("Num. reps") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = exerciseDetails.sets,
            onValueChange = { onFieldValueChange(exerciseDetails.copy(sets = it)) },
            label = { Text("Num. sets") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = exerciseDetails.youtubeUrl,
            onValueChange = { onFieldValueChange(exerciseDetails.copy(youtubeUrl = it)) },
            label = { Text("YouTube url") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}

fun convertToEmbedUrl(youtubeUrl: String): String {
    return when {
        youtubeUrl.contains("embed/") -> youtubeUrl
        youtubeUrl.contains("watch?v=") -> {
            val videoId = youtubeUrl.substringAfter("v=").substringBefore("&")
            "https://www.youtube.com/embed/$videoId"
        }
        else -> ""
    }
}
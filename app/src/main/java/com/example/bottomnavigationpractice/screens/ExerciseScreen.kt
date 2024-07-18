package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bottomnavigationpractice.data.AppViewModelProvider
import com.example.bottomnavigationpractice.data.Exercise
import com.example.bottomnavigationpractice.navigation.GoBackTopAppBar
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseDetails
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseUiState
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseViewModel
import com.example.bottomnavigationpractice.screens.viewmodels.RoutineDetailsViewModel
import com.example.bottomnavigationpractice.video.YoutubePlayer
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    exerciseId: Long,
    navigateBack: () -> Unit,
    viewModel: ExerciseViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            GoBackTopAppBar(title = "",
                canNavigateBack = true,
                navigateUp = navigateBack,

            )
        }
    ){innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ExerciseBody(
                viewModel.exerciseUiState,
                onValueChange = viewModel::updateExerciseUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateExercise()
                    }
                },
                onDeleteClick = {
                    coroutineScope.launch {
                        viewModel.deleteExercise()
                        navigateBack()
                    }
                }
            )
        }

    }
}
@Composable
fun ExerciseBody(
    exerciseUiState: ExerciseUiState,
    onValueChange: (ExerciseDetails) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    var editMode by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(16.dp)

    ){
        if(editMode){
            EditExerciseFields(
                    exerciseDetails = exerciseUiState.exerciseDetails,
                    onValueChange = onValueChange,
                )
            Row(
                modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
                ){
                Button(
                    onClick = {onSaveClick().also{editMode = !editMode}},
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(2f)
                )
                {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
                Button(
                    onClick = {onDeleteClick()},
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)

                )
                {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
        else{
            ViewExercise(
                modifier = Modifier.padding(0.dp),
                exerciseDetails = exerciseUiState.exerciseDetails
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ){
                Button(
                    onClick = {editMode = !editMode},
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }

        }




    }

}

@Composable
fun ViewExercise(
    modifier: Modifier,
    exerciseDetails: ExerciseDetails
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(50.dp),
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(top = 40.dp)
        ) {

            YoutubePlayer(
                youtubeVideoUrl = exerciseDetails.youtubeUrl,
                lifecycleOwner = LocalLifecycleOwner.current,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(text = exerciseDetails.name, fontSize = 30.sp)

            Text(text = "${exerciseDetails.sets} Sets x ${exerciseDetails.reps}", fontSize = 24.sp)
        }


    }

}
@Composable
fun EditExerciseFields(
    exerciseDetails: ExerciseDetails,
    onValueChange: (ExerciseDetails) -> Unit,
    enabled: Boolean = true

){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        OutlinedTextField(
            value = exerciseDetails.name,
            onValueChange = { onValueChange(exerciseDetails.copy(name = it)) },
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
            onValueChange = { onValueChange(exerciseDetails.copy(reps = it)) },
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
            onValueChange = { onValueChange(exerciseDetails.copy(sets = it)) },
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
            onValueChange = { onValueChange(exerciseDetails.copy(youtubeUrl = it)) },
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
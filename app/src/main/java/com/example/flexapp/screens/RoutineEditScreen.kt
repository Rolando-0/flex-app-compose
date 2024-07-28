
/**
 * A Screen in the app itself, which can be arrived at from the Routine screen in
 * RoutineDetailsScreen.kt
 *
 * The screen itself is composed of a form where the user can edit the information of a routine,
 * meaning its name and description and then save the changes.
 *
 * Collects state and makes database queries
 * from a viewModel object in RoutineEditViewModel.kt
 *
 **/


package com.example.flexapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flexapp.database.AppViewModelProvider
import com.example.flexapp.navigation.GoBackTopAppBar
import com.example.flexapp.screens.viewmodels.RoutineDetails
import com.example.flexapp.screens.viewmodels.RoutineEditViewModel
import com.example.flexapp.screens.viewmodels.RoutineEntryUiState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineEditScreen(
    routineId: Long,
    routineName: String,
    navigateBack: () -> Unit,
    viewModel: RoutineEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { GoBackTopAppBar(title = "Edit $routineName", canNavigateBack = true, navigateUp = navigateBack) },
        )
    {innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            RoutineEditBody(
                routineEditUiState = viewModel.routineEditUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateRoutine()
                        navigateBack()
                        navigateBack()
                    }
                },
                onDeleteClick = {
                    coroutineScope.launch {
                        viewModel.deleteRoutine()
                        navigateBack()
                        navigateBack()
                    }
                },
                onFieldValueChange = viewModel::updateRoutineEditState,
                modifier = Modifier
                    .padding(8.dp)

            )
        }
    }

}

@Composable
fun RoutineEditBody(
    routineEditUiState: RoutineEntryUiState,
    onFieldValueChange: (RoutineDetails) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier,
){
    Column(
        modifier = Modifier.padding(8.dp)
    ){
        EditRoutineFields(
            routineEditUiState.routineDetails,
            modifier = Modifier.padding(8.dp),
            onFieldValueChange = onFieldValueChange
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ){
            Button(
                onClick = onSaveClick,
                enabled = routineEditUiState.isEntryValid,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(2f)
            )
            {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
            Button(
                onClick = onDeleteClick,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)

            ){
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }



}

@Composable
fun EditRoutineFields(
    routineDetails: RoutineDetails,
    modifier: Modifier = Modifier,
    onFieldValueChange: (RoutineDetails) -> Unit,
    enabled: Boolean = true
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        OutlinedTextField(
            value = routineDetails.name,
            onValueChange = { onFieldValueChange(routineDetails.copy(name = it)) },
            label = { Text("Routine Name") },
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
            value = routineDetails.desc,
            onValueChange = { onFieldValueChange(routineDetails.copy(desc = it)) },
            label = { Text("Description") },
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
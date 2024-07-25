/**
 * A Screen in the app itself, which can be arrived at from the Routine screen in
 * RoutineScreen.kt
 *
 * The screen itself is composed of a form where the user can type in the information of a new
 * Routine to insert into the list routines displayed in the routine screen in RoutineScreen.kt.
 *
 * Collects state and makes database queries
 * from a viewModel object in RoutineEntryViewModel.kt
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bottomnavigationpractice.data.AppViewModelProvider
import com.example.bottomnavigationpractice.navigation.GoBackTopAppBar
import com.example.bottomnavigationpractice.screens.viewmodels.RoutineDetails
import com.example.bottomnavigationpractice.screens.viewmodels.RoutineEntryUiState
import com.example.bottomnavigationpractice.screens.viewmodels.RoutineEntryViewModel
import com.example.bottomnavigationpractice.ui.theme.BottomNavigationPracticeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineEntryScreen(
    navigateBack: () -> Unit,
    viewModel: RoutineEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            GoBackTopAppBar(
                title = "Add New Routine",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
        ){innerPadding ->
        RoutineEntryBody(
            routineEntryUiState = viewModel.routineEntryUiState,
            onFieldValueChange = viewModel::updateRoutineEntryState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveRoutine()
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
fun RoutineEntryBody(
    routineEntryUiState: RoutineEntryUiState,
    onFieldValueChange: (RoutineDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier

){
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        RoutineEntryForm(
            routineDetails = routineEntryUiState.routineDetails,
            onValueChange = onFieldValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = routineEntryUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Composable
fun RoutineEntryForm(
    routineDetails: RoutineDetails,
    modifier: Modifier = Modifier,
    onValueChange: (RoutineDetails) -> Unit = {},
    enabled: Boolean = true
    ){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        OutlinedTextField(
            value = routineDetails.name,
            onValueChange = { onValueChange(routineDetails.copy(name = it)) },
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
            onValueChange = { onValueChange(routineDetails.copy(desc = it)) },
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

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    BottomNavigationPracticeTheme {
        RoutineEntryBody(
            routineEntryUiState =
            RoutineEntryUiState(
                RoutineDetails(
                name = "Monday", desc = "Leg day, lower body"
            )
        ),
            onFieldValueChange = {},
            onSaveClick = {},
            modifier = Modifier
        )
    }
}

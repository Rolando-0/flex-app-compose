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


package com.example.flexapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flexapp.database.AppViewModelProvider
import com.example.flexapp.database.entities.LibraryExercise
import com.example.flexapp.navigation.GoBackTopAppBar
import com.example.flexapp.screens.viewmodels.ExerciseDetails
import com.example.flexapp.screens.viewmodels.ExerciseEntryUiState
import com.example.flexapp.screens.viewmodels.ExerciseEntryViewModel
import com.example.flexapp.screens.components.YoutubeThumbnail
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEntryScreen(
    routineId: Long,
    navigateBack: () -> Unit,
    viewModel: ExerciseEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()


    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchedLibrary by viewModel.searchedLibrary.collectAsState()

    var showLibrary by remember{ mutableStateOf(false) }

    Scaffold(
        topBar = { GoBackTopAppBar(title = "Add new exercise", canNavigateBack = true, navigateUp = navigateBack)}
    ){innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
        ) {
                item{
                    ExerciseEntryBody(
                        exerciseEntryUiState = viewModel.exerciseEntryUiState,
                        onFieldValueChange = viewModel::updateExerciseEntryState,
                        onSaveClick = {
                            coroutineScope.launch {
                                val url = viewModel.exerciseEntryUiState.exerciseDetails.youtubeUrl
                                val updatedDetails =
                                    viewModel.exerciseEntryUiState.exerciseDetails.copy(
                                        youtubeUrl = convertToEmbedUrl(url)
                                    )
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
                            .fillMaxWidth(),
                    )
                }

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Text(text = "Exercise Library")
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = if (showLibrary) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (showLibrary) "Collapse" else "Expand",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { showLibrary = !showLibrary }
                        )
                    }
                    LaunchedEffect(Unit) {
                        viewModel.loadExercises(context)
                    }
                    if(showLibrary) {
                        AddFromLibrary(
                            exerciseDetails = viewModel.exerciseEntryUiState.exerciseDetails,
                            searchedLibrary = searchedLibrary,
                            onUpdateExercise = viewModel::updateExerciseEntryState,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { query -> viewModel.updateSearchQuery(query) }
                        )
                    }
                }
            }

        }



}
@Composable
fun ExerciseEntryBody(
    exerciseEntryUiState: ExerciseEntryUiState,
    onFieldValueChange: (ExerciseDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
        verticalArrangement = Arrangement.spacedBy(4.dp)
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

@Composable
private fun AddFromLibrary(
    exerciseDetails: ExerciseDetails,
    searchedLibrary: List<LibraryExercise>,
    onUpdateExercise: (ExerciseDetails) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
)
{

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp, max = 400.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { query -> onSearchQueryChange(query) },
            label = { Text("Search Library") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
        )

        Spacer(modifier = Modifier.height(4.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),

        ) {
            items(searchedLibrary) { libraryExercise ->
                LibraryExerciseItem(
                    libraryExercise = libraryExercise,
                    exerciseDetails = exerciseDetails,
                    onUpdateExercise = onUpdateExercise

                )
            }
        }
    }

}
@Composable
private fun LibraryExerciseItem(
    libraryExercise: LibraryExercise,
    exerciseDetails: ExerciseDetails,
    onUpdateExercise: (ExerciseDetails) -> Unit,
){
    Column(
        modifier = Modifier
            .padding(4.dp)
    ){
        Text(text = libraryExercise.name, textAlign = TextAlign.Center)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onUpdateExercise(
                        exerciseDetails.copy(
                            name = libraryExercise.name,
                            youtubeUrl = libraryExercise.youtubeUrl
                        )
                    )
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ){
            Column(){
                YoutubeThumbnail(
                    youtubeUrl = libraryExercise.youtubeUrl,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = libraryExercise.targets,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }



        }


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
package com.example.bottomnavigationpractice.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bottomnavigationpractice.data.AppViewModelProvider
import com.example.bottomnavigationpractice.data.Exercise
import com.example.bottomnavigationpractice.navigation.GoBackTopAppBar
import com.example.bottomnavigationpractice.screens.viewmodels.RoutineDetailsViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

/**
 * A Screen in the app itself, which can be arrived at from the Routine Screen in
 * RoutineScreen.kt
 *
 * It will display a list of all the exercises in the routine as well as a video preview
 * of each exercise
 *
 * Collects state and makes database queries to load the current routine details (List of displayed exercises)
 * from a viewModel object in RoutineDetailsViewModel.kt
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetailsScreen(
    routineId: Long,
    routineName: String,
    routineDesc: String,
    navigateBack: () -> Unit,
    navigateToExerciseEntry: (Long) -> Unit,
    navigateToExercise: (Long) -> Unit,
    navigateToEditRoutine: () -> Unit,
    viewModel: RoutineDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val routineDetailsUiState by viewModel.routineDetailsUiState.collectAsState()

    Scaffold(
        topBar = {
            GoBackTopAppBar(
                title = routineName,
                canNavigateBack = true,
                navigateUp = navigateBack,
                allowEdit = true,
                editAction = navigateToEditRoutine
            )
                 },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navigateToExerciseEntry(routineId)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Routine"
                )
            }
        }


    ){innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            ExercisesBody(
                routineDesc = routineDesc,
                exerciseList = routineDetailsUiState.exercises,
                onExerciseClick = navigateToExercise,
                modifier = Modifier
                    .padding(16.dp)
            )

        }
    }
}

@Composable
private fun ExercisesBody(
    routineDesc: String,
    exerciseList: List<Exercise>,
    onExerciseClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        if(exerciseList.isEmpty()){
            Text(
                text = "No exercises added yet",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            Text(text = routineDesc, modifier = Modifier.padding(vertical = 8.dp))
            ExerciseList(
                exerciseList = exerciseList,
                onExerciseClick = onExerciseClick,
                contentPadding = contentPadding
            )
        }
    }
}
@Composable
private fun ExerciseList(
    exerciseList: List<Exercise>,
    onExerciseClick: (Long) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,

){
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(items = exerciseList, key = { it.exerciseId }){ exercise ->
            ExerciseItem(
                exercise = exercise,
                onExerciseClick = onExerciseClick,
            )
        }
    }
}

@Composable
private fun ExerciseItem(
    exercise: Exercise,
    onExerciseClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
){

    val hasAppeared = remember { mutableStateOf(false) }

    // Set the initial state for the animation
    LaunchedEffect(Unit) {
        hasAppeared.value = true
    }

    AnimatedVisibility(
        visible = hasAppeared.value,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clickable { onExerciseClick(exercise.exerciseId) },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = modifier.padding(0.dp)) {
                Card(
                    modifier = modifier.padding(end = 16.dp)
                ) {
                    YoutubeThumbnail(youtubeUrl = exercise.youtubeUrl)
                }
                Column(modifier = modifier.padding(8.dp)) {
                    Text(
                        text = exercise.name,
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${exercise.sets} Sets x ${exercise.reps}",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

        }
    }
}

fun getYoutubeThumbnailUrl(youtubeUrl: String): String {
    val videoId = when {
        youtubeUrl.contains("embed/") -> youtubeUrl.substringAfter("embed/").substringBefore("?")
        youtubeUrl.contains("watch?v=") -> youtubeUrl.substringAfter("v=").substringBefore("&")
        else -> throw IllegalArgumentException("Invalid YouTube URL")
    }
    return "https://img.youtube.com/vi/$videoId/0.jpg"
}

@Composable
fun YoutubeThumbnail(youtubeUrl: String, modifier: Modifier = Modifier) {
    val thumbnailUrl = getYoutubeThumbnailUrl(youtubeUrl)
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUrl)
                .crossfade(true)
                .build()
        ),
        contentDescription = "YouTube Thumbnail",
        contentScale = ContentScale.Crop,
        modifier = modifier.size(100.dp)
    )
}
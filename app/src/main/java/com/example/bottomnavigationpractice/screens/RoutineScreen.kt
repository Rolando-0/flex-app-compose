/**
 * A Screen in the app itself, which can be arrived at by selecting the icon in the Bottom navigation bar
 * in BottomNavBar.kt. It is the starting destination screen of the app.
 *
 * The screen itself is composed of the list of routines,
 * which can be clicked to navigate to a specific routine itself.
 * A routine can be added from this screen.
 *
 * Collects state and makes database queries
 * from a viewModel object in RoutinesViewModel.kt
 *
 **/

package com.example.bottomnavigationpractice.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bottomnavigationpractice.data.AppViewModelProvider
import com.example.bottomnavigationpractice.data.Routine
import com.example.bottomnavigationpractice.screens.viewmodels.RoutinesViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.tooling.preview.Preview
import com.example.bottomnavigationpractice.R
import com.example.bottomnavigationpractice.ui.theme.BottomNavigationPracticeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen(
    navigateToRoutineEntry: () -> Unit,
    navigateToRoutineDetails: (Long,String,String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RoutinesViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val routineUiState by viewModel.routineUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Workout Routine")
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo ),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp) // Adjust the size as needed
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToRoutineEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Routine"
                )
            }
        },
    ) {innerPadding ->
        RoutinesBody(
            routineList = routineUiState.routineList,
            onRoutineClick = navigateToRoutineDetails,
            contentPadding = innerPadding,
            modifier = Modifier
                .padding(16.dp)

        )




    }
}



@Composable
private fun RoutinesBody(
    routineList: List<Routine>,
    onRoutineClick: (Long, String, String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (routineList.isEmpty()) {
            Text(
                text = "No routines created added yet",
                textAlign = TextAlign.Center,
            )
        } else {
            RoutineList(
                routineList = routineList,
                onRoutineClick = onRoutineClick,
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }

}

@Composable
private fun RoutineList(
    routineList: List<Routine>,
    onRoutineClick: (Long,String,String) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier
){
    LazyColumn(
        modifier = Modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(items = routineList, key = {it.routineId}){ routine ->
            RoutineItem(
                routine = routine,
                onRoutineClick = onRoutineClick
            )

        }
    }
}

@Composable
private fun RoutineItem(
    routine: Routine, modifier: Modifier = Modifier, onRoutineClick: (Long,String,String) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onRoutineClick(routine.routineId, routine.name, routine.desc) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =  Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = routine.name,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = routine.desc,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutinesBodyPreview(){

    BottomNavigationPracticeTheme {
        RoutinesBody(listOf(
            Routine(1,"Monday","Legs"),
            Routine(2,"Tuesday","Upper Body"),
            Routine(3,"Wednesday","Legs"),
            Routine(4,"Thursday","Upper Body"),
            )
            , onRoutineClick = {id,name,desc -> {} })
    }
}
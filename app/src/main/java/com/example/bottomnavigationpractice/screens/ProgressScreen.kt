package com.example.bottomnavigationpractice.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bottomnavigationpractice.R
import com.example.bottomnavigationpractice.data.AppViewModelProvider
import com.example.bottomnavigationpractice.data.Exercise
import com.example.bottomnavigationpractice.data.ProgressItem
import com.example.bottomnavigationpractice.data.Routine
import com.example.bottomnavigationpractice.screens.viewmodels.ProgressDetails
import com.example.bottomnavigationpractice.screens.viewmodels.ProgressEntryUiState
import com.example.bottomnavigationpractice.screens.viewmodels.ProgressViewModel
import com.example.bottomnavigationpractice.screens.viewmodels.RoutinesViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navigateToProgressItem: (Long,String,String) -> Unit,
    viewModel: ProgressViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    var editMode: Boolean by remember { mutableStateOf(false) }
    var addItemDialog by remember { mutableStateOf(false) }

    val progressUiState by viewModel.progressUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Progress Tracker")
                }
                ,actions = {
                    IconButton(onClick = { editMode = !editMode }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addItemDialog = true },
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
        ProgressBody(
            modifier = Modifier.padding(innerPadding),
            onItemClick = navigateToProgressItem,
            progressItemList = progressUiState.progressItemList,
            onDeleteItem = { progressItem: ProgressItem ->
                coroutineScope.launch {
                    viewModel.deleteProgressItem(progressItem)
                }
            },
            editMode = editMode
        )

        if (addItemDialog) {
            editMode = false
            AddItemDialog(
                progressEntryUiState = viewModel.progressEntryUiState,
                onSaveItem = {
                    coroutineScope.launch {
                        viewModel.saveProgressItem()
                        addItemDialog = false
                    }
                },
                onDismiss = { addItemDialog = false },
                onValueChange = viewModel::updateProgressEntryState
            )
        }
    }

}


@Composable
private fun ProgressBody(
    progressItemList: List<ProgressItem>,
    onItemClick: (Long,String,String) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteItem: (ProgressItem) -> Unit,
    editMode: Boolean
){
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (progressItemList.isEmpty()) {
            Text(
                text = "No progress trackers added yet",
                textAlign = TextAlign.Center,
            )
        } else {
            ProgressItemList(
                progressItemList = progressItemList,
                onItemClick = onItemClick,
                editMode = editMode,
                onDeleteItem = onDeleteItem,
                modifier = Modifier.padding(horizontal = 8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun ProgressItemList(
    progressItemList: List<ProgressItem>,
    onItemClick: (Long, String, String) -> Unit,
    editMode: Boolean,
    onDeleteItem: (ProgressItem) -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues
){
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ){
        items(items = progressItemList, key = {it.progressItemId}){ progressItem ->
            ProgressItemCard(
                progressItem = progressItem,
                navigateToItem = onItemClick,
                editMode = editMode,
                onDeleteItem = onDeleteItem
            )

        }
    }
}

@Composable
private fun ProgressItemCard(
    progressItem: ProgressItem,
    editMode: Boolean,
    navigateToItem: (Long, String, String) -> Unit,
    onDeleteItem: (ProgressItem) -> Unit,
    modifier: Modifier = Modifier,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                navigateToItem(
                    progressItem.progressItemId,
                    progressItem.name,
                    progressItem.valueType
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =  Arrangement.spacedBy(8.dp)
        ){
            Row(

            ){
                Text(
                    text = progressItem.name,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(4f)
                )
                if(editMode){
                    Card(
                        modifier = Modifier.clickable { onDeleteItem(progressItem)  }
                    ){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }

            }


        }
    }
}

@Composable
fun AddItemDialog(
    progressEntryUiState: ProgressEntryUiState,
    onSaveItem: () -> Unit,
    onDismiss: () -> Unit,
    onValueChange: (ProgressDetails) -> Unit = {}
)
{


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add New Progress Tracker") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    value = progressEntryUiState.progressDetails.name,
                    onValueChange = { onValueChange(progressEntryUiState.progressDetails.copy(name = it)) },
                    label = { Text("Name of Metric") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = progressEntryUiState.progressDetails.valueType,
                    onValueChange = { onValueChange(progressEntryUiState.progressDetails.copy(valueType = it)) },
                    label = { Text("Value of metric") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSaveItem()
                },
                enabled = progressEntryUiState.isEntryValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bottomnavigationpractice.data.ProgressDataPoint
import com.example.bottomnavigationpractice.navigation.GoBackTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressItemScreen(
    navigateBack: () -> Unit,
    progressItemId: Long,
    progressItemName: String,
    progressValueType: String,
    navigateToEditProgressItem: () -> Unit
){
    Scaffold(
        topBar = {
            GoBackTopAppBar(
                title = progressItemName,
                canNavigateBack = true,
                navigateUp = navigateBack,
                allowEdit = true,
                editAction = navigateToEditProgressItem
            )
        }
    ) {innerPadding ->
        ProgressItemBody(modifier = Modifier.padding(innerPadding))

    }
}

@Composable
fun ProgressItemBody(
    modifier: Modifier,
    //dataPoints: List<ProgressDataPoint>
){
    Column(
        modifier = modifier
    ){
        
    }
}
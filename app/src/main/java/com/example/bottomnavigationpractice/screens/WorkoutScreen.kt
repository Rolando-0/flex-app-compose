package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bottomnavigationpractice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("  Workout")
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp) // Adjust the size as needed
                    )
                }
            )
        },
    ) { innerPadding ->
        WorkoutContent(innerPadding)
    }
}
@Composable
fun WorkoutContent(innerPadding: PaddingValues) {
}
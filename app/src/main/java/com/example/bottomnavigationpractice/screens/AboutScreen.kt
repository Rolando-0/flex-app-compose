package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bottomnavigationpractice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("  About")
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.aboutlogo),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp) // Adjust the size as needed
                    )
                }
            )
        },
    ) { innerPadding ->
        AboutContent(innerPadding)
    }
}

@Composable
fun AboutContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("This application is a Flex Workout App that uses Kotlin.", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("The developers for this app are the following:", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("- Reinniel Candelario", fontSize = 16.sp)
        Text("- Rolando Ruiz", fontSize = 16.sp)
        Text("- Amanda Garcia Fernandez", fontSize = 16.sp)
        Text("- Raul Lizazo", fontSize = 16.sp)
        Text("- Daniel Torres", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Copyright © 2024", fontSize = 18.sp)
    }
}

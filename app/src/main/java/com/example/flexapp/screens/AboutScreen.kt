/**
 * AboutScreen is a composable function that provides a Scaffold layout with a TopAppBar and
 * displays information about the application and its developers.
 */

package com.example.flexapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flexapp.R

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

/**
 * AboutContent is a composable function that provides the content of the About screen,
 * including a description of the application and a list of its developers.
 */
@Composable
fun AboutContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("This project is called Flex Workout Application. This application’s purpose is to help people organize their workout plan. Having a way to arrange by workout routine. Inside the workout routine, you could have a plethora of workouts. You could also calculate your calories with the basal metabolic rate formula. You can also find your One Rep Max with the calculator. ", fontSize = 18.sp)
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

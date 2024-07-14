package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.bottomnavigationpractice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaceScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("  Calorie Calculator")
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calclogo),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp) // Adjust the size as needed
                    )
                }
            )
        },
    ) { innerPadding ->
        CalcContent(innerPadding)
    }
}

@Composable
fun CalcContent(innerPadding: PaddingValues) {
    var gender by remember { mutableStateOf("Male") }
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var height by remember { mutableStateOf(TextFieldValue()) }
    var age by remember { mutableStateOf(TextFieldValue()) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender == "Male",
                onClick = { gender = "Male" }
            )
            Text("Male")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = gender == "Female",
                onClick = { gender = "Female" }
            )
            Text("Female")
        }

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = weight,
            onValueChange = { weight = it },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (weight.text.isEmpty()) {
                        Text("Weight (kg)")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = height,
            onValueChange = { height = it },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (height.text.isEmpty()) {
                        Text("Height (cm)")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = age,
            singleLine = true,
            onValueChange = { age = it },
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (age.text.isEmpty()) {
                        Text("Age")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val weightValue = weight.text.toFloatOrNull()
                val heightValue = height.text.toFloatOrNull()
                val ageValue = age.text.toFloatOrNull()

                if (weightValue != null && heightValue != null && ageValue != null) {
                    val bmr = if (gender == "Male") {
                        66.5 + (13.75 * weightValue) + (5.003 * heightValue) - (6.75 * ageValue)
                    } else {
                        655.1 + (9.563 * weightValue) + (1.850 * heightValue) - (4.676 * ageValue)
                    }
                    result = "$bmr This is your Basal Metabolic Rate. This is optimal calorie intake for weight loss."
                } else {
                    result = "Please enter valid values for weight, height, and age."
                }
            }
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result)
    }
}

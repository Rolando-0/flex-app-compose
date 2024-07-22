/**
 * FaceScreen is a composable function that provides a Scaffold layout with a TopAppBar and
 * dynamically displays content based on the selected item from a dropdown menu. It offers
 * options for a Calorie Calculator and a One Rep Max Calculator.
 */

package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.bottomnavigationpractice.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaceScreen() {
    // State variables to manage dropdown menu expansion and selected item
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("BMR Calculator") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = selectedItem)
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calclogo),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp) // Adjust the size as needed
                    )
                },
                actions = {
                    Box(modifier = Modifier.padding(end = 16.dp)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "BMR Calculator") },
                                onClick = { selectedItem = "BMR Calculator" },
                            )
                            DropdownMenuItem(
                                text = { Text(text = "One Rep Max Calculator") },
                                onClick = { selectedItem = "One Rep Max Calculator" },
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        // Display content based on the selected item
        when (selectedItem) {
            "BMR Calculator" -> BmrContent(innerPadding)
            "One Rep Max Calculator" -> OneRepMaxCalculatorContent(innerPadding)
            else -> {}
        }
    }
}

/**
 * OneRepMaxCalculatorContent is a composable function that provides the UI for calculating
 * the one rep max based on user input of weight and repetitions.
 */
@Composable
fun OneRepMaxCalculatorContent(innerPadding: PaddingValues) {
    // State variables to manage user inputs and result
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var reps by remember { mutableStateOf(TextFieldValue()) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            singleLine = true,
            value = weight,
            onValueChange = { weight = it },
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
                        Text("Weight (lbs)")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            singleLine = true,
            value = reps,
            onValueChange = { reps = it },
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
                    if (reps.text.isEmpty()) {
                        Text("Repetitions")
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val weightValue = weight.text.toFloatOrNull()
                val repsValue = reps.text.toIntOrNull()

                if (weightValue != null && repsValue != null) {
                    // Calculate One Rep Max using the Epley formula
                    val oneRepMax = weightValue * (1 + repsValue / 30f)
                    result = "1 X ${oneRepMax.toInt()} is your One Rep Max"
                } else {
                    result = "Please enter valid values for weight and repetitions."
                }
            }
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result)
    }
}

/**
 * CalcContent is a composable function that provides the UI for calculating the Basal Metabolic
 * Rate (BMR) based on user inputs of gender, weight, height, and age.
 */
@Composable
fun BmrContent(innerPadding: PaddingValues) {
    // State variables to manage user inputs and result
    var gender by remember { mutableStateOf("Male") }
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var height by remember { mutableStateOf(TextFieldValue()) }
    var age by remember { mutableStateOf(TextFieldValue()) }
    var bmr by remember { mutableStateOf(0.0)}



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
            singleLine = true,
            value = weight,
            onValueChange = { weight = it },
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
            singleLine = true,
            value = height,
            onValueChange = { height = it },
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
            singleLine = true,
            value = age,
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

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val weightValue = weight.text.toFloatOrNull()
                val heightValue = height.text.toFloatOrNull()
                val ageValue = age.text.toFloatOrNull()

                if (weightValue != null && heightValue != null && ageValue != null) {
                    // Calculate Basal Metabolic Rate (BMR) based on gender
                    if (gender == "Male") {
                       bmr = 66.5 + (13.75 * weightValue) + (5.003 * heightValue) - (6.75 * ageValue)
                    } else {
                        bmr = 655.1 + (9.563 * weightValue) + (1.850 * heightValue) - (4.676 * ageValue)
                    }
                } else {
                    bmr = 0.0

                }
            }

        ) {
            Text("Calculate")
        }

        if(bmr != 0.0){
            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .verticalScroll(scrollState)
            ){
                Text(
                    text = "${TwoDecimalPt(bmr)} cal/day is your Basal Metabolic Rate,\n" +
                            "However, this varies based level of daily\n" +
                            "activity:",
                    maxLines = 10
                )
                Text(text = "Sedentary: ${TwoDecimalPt(bmr * 1.2)} cal/day")
                Text(text = "Lightly Active: ${TwoDecimalPt(bmr * 1.375)} cal/day")
                Text(text = "Moderately Active: ${TwoDecimalPt(bmr * 1.55)} cal/day")
                Text(text = "Very Active: ${TwoDecimalPt(bmr * 1.725)} cal/day")
            }
        } else {
            Text("Please enter a valid height and weight to calculate your BMR")
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}
fun TwoDecimalPt(value: Double): Double {
    return (value * 100).toInt() / 100.0
}

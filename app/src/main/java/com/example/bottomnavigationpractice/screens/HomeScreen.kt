package com.example.bottomnavigationpractice.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Column(verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth())
        {
        Button(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally) ){
            Text("Button 1")
        }
        Button(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally) ){
            Text("Button 2")
        }
    }
}
package com.example.bottomnavigationpractice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavigationpractice.screens.ChatScreen
import com.example.bottomnavigationpractice.screens.HomeScreen
import com.example.bottomnavigationpractice.screens.SettingsScreen


@Composable
fun WorkOutNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    ){
    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ){
        composable(route = "Home"){
            HomeScreen()
        }
        composable(route = "Settings"){
            SettingsScreen()
        }
        composable(route = "Chat"){
            ChatScreen()
        }
    }

}
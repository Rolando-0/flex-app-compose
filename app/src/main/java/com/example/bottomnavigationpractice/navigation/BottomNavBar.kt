package com.example.bottomnavigationpractice.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.bottomnavigationpractice.BottomNavigationItem


@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf( //Bottom navigation bar items
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews =  false,
        ),
        BottomNavigationItem(
            title = "Chat",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews =  false,
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews =  false,
        ),
    )
    var selectedItemIndex by rememberSaveable{ //Remembers state of selected bottom nav bar item
        mutableStateOf(0)
    }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.title)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if(index == selectedItemIndex){
                            item.selectedIcon
                        }else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }

            )

        }
    }
}
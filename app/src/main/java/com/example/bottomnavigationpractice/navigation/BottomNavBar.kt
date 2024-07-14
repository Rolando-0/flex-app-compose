package com.example.bottomnavigationpractice.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
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
            title = "Workout",
            selectedIcon = Icons.Filled.Check,
            unselectedIcon = Icons.Outlined.Check,
            hasNews =  false,
        ),
        BottomNavigationItem(
            title = "Calc",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face,
            hasNews =  false,
        ),
        BottomNavigationItem(
            title = "About",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
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
                    when(item.title){
                        "Workout" -> navController.navigate(RoutinesRoute)
                        "Calc" -> navController.navigate(CalcRoute)
                        "About" -> navController.navigate(AboutRoute)
                    }

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
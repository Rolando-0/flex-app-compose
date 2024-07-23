/**
 * BottomNavBar is a composable function that creates a bottom navigation bar with three items:
 * Workout, Calc, and Progress. When each item is clicked, respectively they will navigate to the following:
 *
 * 'Workout' -> RoutinesRoute
 * 'Calc' -> CalcRoute
 * 'Progress' -> ProgressRoute
 *
 * See 'MainNavGraph.kt' to learn more about what these routes do
 *
 * when an item is selected
 * by the user.
 */

package com.example.bottomnavigationpractice.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
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
    // List of bottom navigation items
    val items = listOf(
        BottomNavigationItem(
            title = "Workout",
            selectedIcon = Icons.Filled.Check,
            unselectedIcon = Icons.Outlined.Check,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Calc",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Progress",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            hasNews = false,
        ),
    )

    // State variable to remember the selected bottom nav bar item
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    // Navigate to the respective route based on the selected item
                    when(item.title) {
                        "Workout" -> navController.navigate(RoutinesRoute)
                        "Calc" -> navController.navigate(CalcRoute)
                        "Progress" -> navController.navigate(ProgressRoute)
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}

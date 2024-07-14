package com.example.bottomnavigationpractice.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bottomnavigationpractice.RoutineApplication
import com.example.bottomnavigationpractice.screens.viewmodels.RoutineEntryViewModel
import com.example.bottomnavigationpractice.screens.viewmodels.RoutinesViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RoutinesViewModel(
                routineApplication().container.routineRepository
            )
        }
        initializer {
            RoutineEntryViewModel(
                routineApplication().container.routineRepository
            )
        }

    }
}

fun CreationExtras.routineApplication(): RoutineApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RoutineApplication)
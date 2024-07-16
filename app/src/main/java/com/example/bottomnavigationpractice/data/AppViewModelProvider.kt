package com.example.bottomnavigationpractice.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bottomnavigationpractice.RoutineApplication
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseEntryViewModel
import com.example.bottomnavigationpractice.screens.viewmodels.ExerciseViewModel
import com.example.bottomnavigationpractice.screens.viewmodels.RoutineDetailsViewModel
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
        initializer {
            RoutineDetailsViewModel(
                this.createSavedStateHandle(),
                routineApplication().container.routineRepository
            )
        }
        initializer {
            ExerciseEntryViewModel(
                this.createSavedStateHandle(),
                routineApplication().container.routineRepository
            )
        }
        initializer {
            ExerciseViewModel(
                this.createSavedStateHandle(),
                routineApplication().container.routineRepository
            )
        }

    }
}

//test
fun CreationExtras.routineApplication(): RoutineApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RoutineApplication)
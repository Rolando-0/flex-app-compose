/**
 * A singleton object which contains a factory
 * that is used to initialize each viewModel
 * when needed.
 *
 * ViewModels are used by screens to keep track of state inside Ui
 * and provide an interface for making calls to database
 * */


package com.example.flexapp.database

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flexapp.RoutineApplication
import com.example.flexapp.screens.viewmodels.ExerciseEntryViewModel
import com.example.flexapp.screens.viewmodels.ExerciseViewModel
import com.example.flexapp.screens.viewmodels.ProgressDataViewModel
import com.example.flexapp.screens.viewmodels.ProgressViewModel
import com.example.flexapp.screens.viewmodels.RoutineDetailsViewModel
import com.example.flexapp.screens.viewmodels.RoutineEditViewModel
import com.example.flexapp.screens.viewmodels.RoutineEntryViewModel
import com.example.flexapp.screens.viewmodels.RoutinesViewModel

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
            RoutineEditViewModel(
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
        initializer{
            ProgressViewModel(
                routineApplication().container.progressRepository
            )
        }
        initializer {
            ProgressDataViewModel(
                this.createSavedStateHandle(),
                routineApplication().container.progressRepository
            )
        }


    }
}

fun CreationExtras.routineApplication(): RoutineApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RoutineApplication)
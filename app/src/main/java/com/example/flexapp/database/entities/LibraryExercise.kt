/**
 *  Defines a data class (similar but not the Exercise entity)
 *  which is used to read JSON to put into the library of exercises
 *  in ExerciseEntryScreen.kt
 *
 * */

package com.example.flexapp.database.entities

import kotlinx.serialization.Serializable

@Serializable
data class LibraryExercise(
    val name: String,
    val youtubeUrl: String,
    val targets: String
)
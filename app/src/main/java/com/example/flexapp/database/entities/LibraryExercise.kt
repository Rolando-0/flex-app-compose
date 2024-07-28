package com.example.flexapp.database.entities

import kotlinx.serialization.Serializable

@Serializable
data class LibraryExercise(
    val name: String,
    val youtubeUrl: String,
    val targets: String
)
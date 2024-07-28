
/**
 * A class containing the attributes
 * for the Routine entity.
 *
 * The Exercise entity (in Exercise.kt) use the routineId
 * as a foreign key.
 **/



package com.example.flexapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Routine(
    @PrimaryKey(autoGenerate = true) val routineId: Long = 0,
    val name: String,
    val desc: String
)
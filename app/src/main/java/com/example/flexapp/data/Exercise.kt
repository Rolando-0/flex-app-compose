/**
 * A class containing the attributes
 * for the Exercise entity.
 *
 * It has a foreign key to show the
 * many-to-one relationship it has with Routines,
 * as each Routine contains many exercises.
 *
 **/


package com.example.flexapp.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Routine::class,
        parentColumns = ["routineId"],
        childColumns = ["routineId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) val exerciseId: Long = 0,
    val name: String,
    val reps: String,
    val sets: String,
    val youtubeUrl: String,
    val routineId: Long //Foreign key
)


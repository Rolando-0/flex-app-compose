package com.example.bottomnavigationpractice.data


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
    val reps: Int,
    val sets: Int,
    val youtubeUrl: String,
    val routineId: Long //Foreign key
)


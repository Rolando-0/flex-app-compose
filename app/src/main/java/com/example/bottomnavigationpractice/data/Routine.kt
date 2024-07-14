package com.example.bottomnavigationpractice.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Routine(
    @PrimaryKey(autoGenerate = true) val routineId: Long = 0,
    val name: String,
    val desc: String
)
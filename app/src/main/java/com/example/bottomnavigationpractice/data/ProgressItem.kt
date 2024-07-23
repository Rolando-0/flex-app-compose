package com.example.bottomnavigationpractice.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class ProgressItem(
    @PrimaryKey(autoGenerate = true) val progressItemId: Long = 0,
    val name: String,
    val valueType: String
)
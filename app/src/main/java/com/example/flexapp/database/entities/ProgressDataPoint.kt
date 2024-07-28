package com.example.flexapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [ForeignKey(
        entity = ProgressItem::class,
        parentColumns = ["progressItemId"],
        childColumns = ["progressItemId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class ProgressDataPoint(
    @PrimaryKey(autoGenerate = true) val dataId: Long = 0,
    val value: Int,
    val dateString: String,
    val progressItemId: Long // Foreign key

)

/**
 * A class containing the attributes
 * for the ProgressItem entity.
 *
 * The ProgressDataPoint entity (in ProgressDataPoint.kt) use the progressItemId
 * as a foreign key.
 **/

package com.example.flexapp.database.entities

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
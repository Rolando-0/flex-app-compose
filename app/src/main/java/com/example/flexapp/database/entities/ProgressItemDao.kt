/**
 * An interface to be used by the ProgressRepository object in ProgressRepository.kt,
 * which contains the set of SQL queries used to interact with
 * the 'ProgressItem' and 'ProgressDataPoint' entities.
 *
 * Also a member of the app's database class in RoutineDatabase.kt
 *
 * The queries are annotated with @Query, @Insert, @Delete, etc
 *
 * ie fetching all the data points of a progression item
 * */



package com.example.flexapp.database.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressItemDao {
    //Progress item queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressItem(progressItem: ProgressItem): Long

    @Delete
    suspend fun deleteProgressItem(progressItem: ProgressItem)

    @Query("SELECT * FROM ProgressItem")
    fun getAllProgressItems(): Flow<List<ProgressItem>>

    @Query("SELECT * FROM ProgressItem WHERE progressItemId = :id")
    fun getProgressItemById(id: Long): Flow<ProgressItem?>

    //progress item Data point queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataPoint(progressDataPoint: ProgressDataPoint): Long

    @Delete
    suspend fun deleteDataPoint(progressDataPoint: ProgressDataPoint)

    @Query("SELECT * FROM ProgressDataPoint WHERE progressItemId = :progressItemId")
    fun getDataPointsByProgressItemId(progressItemId: Long): Flow<List<ProgressDataPoint>>

    @Query("SELECT * FROM ProgressDataPoint WHERE dataId = :id")
    fun getDataPointById(id: Long): Flow<ProgressDataPoint?>

}


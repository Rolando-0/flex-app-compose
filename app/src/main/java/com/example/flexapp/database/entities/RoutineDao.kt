/**
 * An interface to be used by the RoutineRepository object in RoutineRepository.kt,
 * which contains the set of SQL queries used to interact with
 * the 'Routine' and 'Exercise' entities.
 *
 * Also a member of the app's database class in RoutineDatabase.kt
 *
 * The queries are annotated with @Query, @Insert, @Delete, etc
 *
 * ie fetching all the exercises in a routine
 * */


package com.example.flexapp.database.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise): Long

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Query("UPDATE Routine SET name = :newName, `desc` = :newDesc WHERE routineId = :routineId")
    suspend fun updateRoutine(routineId: Long, newName: String, newDesc: String)
    @Query("SELECT * FROM Routine")
    fun getAllRoutines(): Flow<List<Routine>>

    @Query("SELECT * FROM Exercise WHERE routineId = :routineId")
    fun getExercisesForRoutine(routineId: Long): Flow<List<Exercise>>

    @Query("SELECT * From Routine WHERE routineId = :routineId")
    fun getRoutineById(routineId: Long): Flow<Routine?>

    @Query("SELECT * FROM Exercise WHERE exerciseId = :exerciseId")
    fun getExerciseById(exerciseId: Long): Flow<Exercise>
    @Delete
    suspend fun deleteRoutine(routine: Routine)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("DELETE FROM Exercise WHERE exerciseId = :exerciseId")
    suspend fun deleteExerciseById(exerciseId: Long)

    @Query("DELETE FROM Routine WHERE routineId = :routineId")
    suspend fun deleteRoutineById(routineId: Long)
}
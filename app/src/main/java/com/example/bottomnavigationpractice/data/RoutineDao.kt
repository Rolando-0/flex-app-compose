package com.example.bottomnavigationpractice.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise): Long
    /* Successful insert of an exercise will return its routine's ID*/
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
}
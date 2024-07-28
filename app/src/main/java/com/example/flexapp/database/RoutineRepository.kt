package com.example.flexapp.database

import com.example.flexapp.database.entities.Exercise
import com.example.flexapp.database.entities.Routine
import com.example.flexapp.database.entities.RoutineDao
import kotlinx.coroutines.flow.Flow

class RoutineRepository (
    private val routineDao: RoutineDao
){
    /* Routine Queries */
    fun getAllRoutines(): Flow<List<Routine>> =
        routineDao.getAllRoutines()

    fun getExercisesForRoutine(id: Long): Flow<List<Exercise>> =
        routineDao.getExercisesForRoutine(id)

    fun getRoutineById(routineId: Long): Flow<Routine?> =
        routineDao.getRoutineById(routineId)
    suspend fun insertRoutine(routine: Routine) =
        routineDao.insertRoutine(routine)

    suspend fun updateRoutine(routineId: Long, routineName: String, routineDesc: String) =
        routineDao.updateRoutine(routineId, routineName, routineDesc)


    suspend fun insertExercise(exercise: Exercise, routineId: Long){
        val routine = routineDao.getRoutineById(routineId)
        if(routine != null){
            routineDao.insertExercise(exercise)
        }

    }
    suspend fun updateExercise(exercise: Exercise){
        routineDao.updateExercise(exercise)
    }
    fun getExerciseById(exerciseId: Long): Flow<Exercise> =
        routineDao.getExerciseById(exerciseId)

    suspend fun deleteRoutine(routine: Routine){
        routineDao.deleteRoutine(routine)
    }

    suspend fun deleteExercise(exercise: Exercise){
        routineDao.deleteExercise(exercise)
    }

    suspend fun deleteRoutineById(routineId: Long){
        routineDao.deleteRoutineById(routineId)
    }

    suspend fun deleteExerciseById(exerciseId: Long){
        routineDao.deleteExerciseById(exerciseId)
    }

    /* Progress tracker queries */





}
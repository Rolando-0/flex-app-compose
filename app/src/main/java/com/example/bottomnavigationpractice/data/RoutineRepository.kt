package com.example.bottomnavigationpractice.data

import kotlinx.coroutines.flow.Flow

class RoutineRepository (
    private val routineDao: RoutineDao
){
    fun getAllRoutines(): Flow<List<Routine>> =
        routineDao.getAllRoutines()

    fun getExercisesForRoutine(id: Long): Flow<List<Exercise>> =
        routineDao.getExercisesForRoutine(id)

    fun getRoutineById(routineId: Long): Flow<Routine?> =
        routineDao.getRoutineById(routineId)
    suspend fun insertRoutine(routine: Routine) =
        routineDao.insertRoutine(routine)


    suspend fun insertExercise(exercise: Exercise, routineId: Long){
        val routine = routineDao.getRoutineById(routineId)
        if(routine != null){
            routineDao.insertExercise(exercise)
        }

    }
    fun getExerciseById(exerciseId: Long): Flow<Exercise> =
        routineDao.getExerciseById(exerciseId)

    suspend fun deleteRoutine(routine: Routine){
        routineDao.deleteRoutine(routine)
    }

    suspend fun deleteExercise(exercise: Exercise){
        routineDao.deleteExercise(exercise)
    }

    suspend fun deleteExerciseById(exerciseId: Long){
        routineDao.deleteExerciseById(exerciseId)
    }





}
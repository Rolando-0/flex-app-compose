/**  A class that implements an AppContainer,
 *  used instantiating a RoutineRepository object
 *  and ProgressRepository object
 *
 *  and wraps both the RoutineRepository and
 *  ProgressRepository into members of a single container
 **/

package com.example.flexapp.database

import android.content.Context

interface AppContainer {
    val routineRepository: RoutineRepository
    val progressRepository: ProgressRepository
}

class AppDataContainer(private val context: Context) : AppContainer{

    override val routineRepository: RoutineRepository by lazy{
        RoutineRepository(RoutineDatabase.getDatabase(context).routineDao())
    }
    override val progressRepository: ProgressRepository by lazy{
        ProgressRepository(RoutineDatabase.getDatabase(context).progressItemDao())
    }
}
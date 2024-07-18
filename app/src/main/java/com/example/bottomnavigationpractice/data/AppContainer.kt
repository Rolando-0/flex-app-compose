/**  A class that implements an AppContainer,
 *  used instantiating a RoutineRepository object
 *  to be passed to viewModels
 *
 **/

package com.example.bottomnavigationpractice.data

import android.content.Context

interface AppContainer {
    val routineRepository: RoutineRepository
}

class AppDataContainer(private val context: Context) : AppContainer{

    override val routineRepository: RoutineRepository by lazy{
        RoutineRepository(RoutineDatabase.getDatabase(context).routineDao())
    }
}
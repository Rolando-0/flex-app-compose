/**
 * A kotlin class that initializes the AppContainer,
 * which contains the the repositories classes that access
 * the database itself.
 * */

package com.example.flexapp

import android.app.Application
import com.example.flexapp.database.AppContainer
import com.example.flexapp.database.AppDataContainer

class RoutineApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}
/**
 * A kotlin class that initializes the AppContainer,
 * which contains the database.
 * */

package com.example.flexapp

import android.app.Application
import com.example.flexapp.data.AppContainer
import com.example.flexapp.data.AppDataContainer

class RoutineApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}
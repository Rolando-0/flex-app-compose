package com.example.bottomnavigationpractice

import android.app.Application
import com.example.bottomnavigationpractice.data.AppContainer
import com.example.bottomnavigationpractice.data.AppDataContainer

class RoutineApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}
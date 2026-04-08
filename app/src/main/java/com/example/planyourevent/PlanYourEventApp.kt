package com.example.planyourevent

import android.app.Application

class PlanYourEventApp : Application() {

    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
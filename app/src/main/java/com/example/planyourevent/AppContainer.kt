package com.example.planyourevent

import android.content.Context
import com.example.planyourevent.data.local.AppDatabase
import com.example.planyourevent.data.repository.EventRepository

class AppContainer(context: Context) {

    private val database = AppDatabase.getDatabase(context)

    val repository = EventRepository(database.eventDao())
}
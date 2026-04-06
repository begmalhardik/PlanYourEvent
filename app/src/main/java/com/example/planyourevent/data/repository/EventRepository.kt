package com.example.planyourevent.data.repository

import com.example.planyourevent.data.local.Event
import com.example.planyourevent.data.local.EventDao
import kotlinx.coroutines.flow.Flow

class EventRepository(private val dao: EventDao) {

    // Flow automatically updates UI when DB changes
    val allEvents: Flow<List<Event>> = dao.getAllEvents()

    suspend fun insert(event: Event) {
        dao.insert(event)
    }

    suspend fun update(event: Event) {
        dao.update(event)
    }

    suspend fun delete(event: Event) {
        dao.delete(event)
    }
}
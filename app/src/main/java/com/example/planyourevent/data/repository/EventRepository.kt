package com.example.planyourevent.data.repository

import com.example.planyourevent.data.local.Event
import com.example.planyourevent.data.local.EventDao
import kotlinx.coroutines.flow.Flow

class EventRepository(private val dao: EventDao) { // middle layer between viewmodel and data sources
    // we will use dao to access DB

    // Flow automatically updates UI when DB changes
    val allEvents: Flow<List<Event>> = dao.getAllEvents()  // return a stream that keeps giving updated list of events whenever DB changes
    // repository  is exposing data stream from database

    // why we are not calling DAO directly in viewmodel ?
    //  Because repository becomes the single source of truth

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
// why we need this Repository ?

// Without repository
// ViewModel → DAO → Database
// Problems:
//  a) ViewModel directly tied to DB
//  b) Hard to change data source later
//  c) Not scalable
//  d) Messy logic

// With repository
// Benefits:
//  Clean separation
//  Easy to maintain
//  Easy to extend (API, caching, etc.)
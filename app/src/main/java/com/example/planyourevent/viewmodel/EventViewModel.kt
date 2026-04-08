package com.example.planyourevent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planyourevent.data.local.Event
import com.example.planyourevent.data.repository.EventRepository
import com.example.planyourevent.util.EventUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventViewModel(
    private val repository: EventRepository // depends on repository - meaning it don't care about DB it asks
) : ViewModel() { // this is a class that holds and manages UI data in a lifecycle-aware way
    // UI (Compose Screen) ↔ ViewModel ↔ Repository
    // it survives configuration changes Rotate screen → UI destroyed → ViewModel survives
    // separates logic from UI that is UI = display only but ViewModel = logic + data handling
    // safe coroutine scope ( viewModelScope.launch { ... } )
    // automatically cancelled when ViewModel is destroyed

    // Expose Flow to UI
    //val allEvents: Flow<List<Event>> = repository.allEvents

    private val _uiState = MutableStateFlow<EventUiState>(EventUiState.Loading)
    val uiState: StateFlow<EventUiState> = _uiState

    init {
        viewModelScope.launch {
            delay(500)
            repository.allEvents.collect { events ->
                _uiState.value = EventUiState.Success(events)
            }
        }
    }

    fun addEvent(event: Event) = viewModelScope.launch { // a lifecycle-aware coroutine scope tied to viewmodel
        repository.insert(event)
    } // we are saying over here to run the app in background (async), don't block UI

    // Lifecycle behaviour:
    //  Screen opens → ViewModel created → Coroutine starts
    //  Screen destroyed → ViewModel destroyed → Coroutine CANCELLED

    fun updateEvent(event: Event) = viewModelScope.launch {
        repository.update(event)
    }


    fun deleteEvent(event: Event) = viewModelScope.launch {
        repository.delete(event)
    }

    fun formatDate(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm")
        return sdf.format(java.util.Date(timestamp))
    }
}
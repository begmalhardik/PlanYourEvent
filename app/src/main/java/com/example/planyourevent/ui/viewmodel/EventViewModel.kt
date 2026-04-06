package com.example.planyourevent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planyourevent.data.local.Event
import com.example.planyourevent.data.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EventViewModel(
    private val repository: EventRepository
) : ViewModel() {

    // Expose Flow to UI
    val allEvents: Flow<List<Event>> = repository.allEvents

    fun addEvent(event: Event) {
        viewModelScope.launch {
            repository.insert(event)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            repository.update(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            repository.delete(event)
        }
    }
}
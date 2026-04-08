package com.example.planyourevent.util

import com.example.planyourevent.data.local.Event

sealed class EventUiState {

    object Loading: EventUiState()
    data class Success(val events: List<Event>) : EventUiState()
    data class Error(val message: String) : EventUiState()
}
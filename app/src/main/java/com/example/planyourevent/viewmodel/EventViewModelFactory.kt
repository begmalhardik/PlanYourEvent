package com.example.planyourevent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.planyourevent.data.repository.EventRepository

// Normally, Android creates ViewModel like this,
//  val viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
// But this only works if:
//  class EventViewModel : ViewModel() -> No CONSTRUCTOR parameter

// PROBLEM ?? : Android does not know "How to pass repository into viewmodel?"
class EventViewModelFactory(
    private val repository: EventRepository // Factory holds the dependency (repository)
) : ViewModelProvider.Factory { // we create this factory which tells Android "when creating ViewModel -> pass this repository"

    override fun <T : ViewModel> create(modelClass: Class<T>): T { // Android calls this method when it needs a ViewModel

        if (modelClass.isAssignableFrom(EventViewModel::class.java)) { // Is the requested ViewModel = EventViewModel?
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repository) as T // we are manually doing EventViewModel(repository), which android couldn't do it by itself
        }

        throw IllegalArgumentException("Unknown ViewModel class")
        // If wrong ViewModel requested → crash safely
    }
} // is used to create viewmodel objects with parameters
//  Factory = A helper that knows HOW to create our viewmodel

// Without Factory
//  Android → create ViewModel (doesn’t know repository)
// With Factory
//  Android → calls Factory → Factory creates ViewModel(repository)
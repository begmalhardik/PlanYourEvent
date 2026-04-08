package com.example.planyourevent.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.planyourevent.PlanYourEventApp
import com.example.planyourevent.data.local.AppDatabase
import com.example.planyourevent.data.repository.EventRepository
import com.example.planyourevent.ui.navigation.AppNavGraph
import com.example.planyourevent.viewmodel.EventViewModel
import com.example.planyourevent.viewmodel.EventViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: EventViewModel by viewModels {
        EventViewModelFactory(
            (application as PlanYourEventApp).container.repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // give me a viewmodel of type EventViewmodel, and if it doesn't exist, create it using this factory
        // viewModelProvider is a lifecycle-aware MANAGER that creates and retains viewmodel for a screen.

        setContent {
            AppNavGraph(viewModel)
        }
    }
}
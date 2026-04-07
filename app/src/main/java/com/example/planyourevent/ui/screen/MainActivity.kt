package com.example.planyourevent.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.planyourevent.data.local.AppDatabase
import com.example.planyourevent.data.repository.EventRepository
import com.example.planyourevent.ui.navigation.AppNavGraph
import com.example.planyourevent.ui.theme.PlanYourEventTheme
import com.example.planyourevent.viewmodel.EventViewModel
import com.example.planyourevent.viewmodel.EventViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)

        val repository = EventRepository(database.eventDao())
        val factory = EventViewModelFactory(repository)

        val viewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]
        // give me a viewmodel of type EventViewmodel, and if it doesn't exist, create it using this factory
        // viewModelProvider is a lifecycle-aware MANAGER that creates and retains viewmodel for a screen.

        setContent {
            AppNavGraph(viewModel)
        }
    }
}
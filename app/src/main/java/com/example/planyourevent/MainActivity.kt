package com.example.planyourevent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.planyourevent.data.local.AppDatabase
import com.example.planyourevent.data.repository.EventRepository
import com.example.planyourevent.ui.theme.PlanYourEventTheme
import com.example.planyourevent.ui.viewmodel.EventViewModel
import com.example.planyourevent.ui.viewmodel.EventViewModelFactory

class MainActivity : ComponentActivity() {

    val database = AppDatabase.getDatabase(applicationContext)
    val repository = EventRepository(database.eventDao())
    val viewModelFactory = EventViewModelFactory(repository)

    val viewModel = ViewModelProvider(this, viewModelFactory)[EventViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanYourEventTheme {

            }
        }
    }
}
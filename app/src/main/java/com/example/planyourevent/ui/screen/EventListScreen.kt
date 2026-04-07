package com.example.planyourevent.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planyourevent.data.local.Event
import com.example.planyourevent.viewmodel.EventViewModel

@Composable
fun EventListScreen(
    viewModel: EventViewModel,
    onAddClick: () -> Unit
) {

    val events by viewModel.allEvents.collectAsStateWithLifecycle(initialValue = emptyList()) // UI auto recomposes

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = onAddClick) {
            Text("+")
        }
    }) { padding ->

        if (events.isEmpty()) {
            Box(modifier = Modifier.padding(padding)) {
                Text("No Events Yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(events) { event ->
                    EventItem(
                        event = event,
                        onDelete = { viewModel.deleteEvent(event) }
                    )
                }
            }
        }
    }

}

@Composable
fun EventItem(
    event: Event,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = event.title, style = MaterialTheme.typography.titleLarge)
            Text(text = event.category)
            Text(text = event.location)
            Text(text = " Time: ${event.dateTime}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}
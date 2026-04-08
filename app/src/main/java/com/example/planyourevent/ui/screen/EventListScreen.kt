package com.example.planyourevent.ui.screen


import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planyourevent.data.local.Event
import com.example.planyourevent.util.EventUiState
import com.example.planyourevent.viewmodel.EventViewModel

@Composable
fun EventListScreen(
    viewModel: EventViewModel,
    onAddClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle() // UI auto recomposes
    // it tells to convert a Flow of events into a compose state, so UI automatically updates whenever data changes
    // flow : emits data over time
    // DB changes → Flow emits new list → again → again → again
    // Problem: Compose CANNOT directly observe Flow
    //  Compose understands, State<T>
    //  But Flow is, Flow<T>
    // So we need conversion
    // .collectAsStateWithLifecycle() converts Flow<List<Event>>  →  State<List<Event>>
    // it says start listening to FLow and give me latest value as State

    when(uiState) {
        is EventUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }

        is EventUiState.Success -> {

            val events = (uiState as EventUiState.Success).events

            Scaffold(floatingActionButton = {
                FloatingActionButton(onClick = onAddClick) {
                    Text("+", fontSize = 20.sp)
                }
            }) { padding ->

                if (events.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Events Yet", fontSize = 16.sp)
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
                                viewModel = viewModel,
                                onDelete = { viewModel.deleteEvent(event) },
                                onClick = {
                                    onItemClick(event.id)
                                }
                            )
                        }
                    }
                }
            }
        }

        is EventUiState.Error -> {
            Text("Something went wrong")
        }
    }

}

@Composable
fun EventItem(
    event: Event,
    viewModel: EventViewModel,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {

        Column(modifier = Modifier.padding(12.dp)) {

            Text(text = "Title: ${event.title}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Category: ${event.category}")
            Text(text = "Location: ${event.location}")
            Text(text = "Time: " + viewModel.formatDate(event.dateTime))

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}
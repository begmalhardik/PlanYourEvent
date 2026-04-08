package com.example.planyourevent.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planyourevent.data.local.Event
import com.example.planyourevent.util.EventUiState
import com.example.planyourevent.viewmodel.EventViewModel
import kotlinx.coroutines.launch

@Composable
fun AddEditEventScreen(
    viewModel: EventViewModel,
    onSave: () -> Boolean,
    eventId: Int
) {

    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val existingEvent = (uiState as? EventUiState.Success)
        ?.events
        ?.find { it.id == eventId }

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var selectedDateTime by remember {
        mutableLongStateOf(existingEvent?.dateTime ?: System.currentTimeMillis())
    }
    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    LaunchedEffect(existingEvent) {
        existingEvent?.let {
            title = it.title
            category = it.category
            location = it.location
            selectedDateTime = it.dateTime
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") }
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val calendar = java.util.Calendar.getInstance()

                val datePicker = android.app.DatePickerDialog(
                    context,
                    { _, y, m, d ->

                        val timePicker = android.app.TimePickerDialog(
                            context,
                            { _, h, min ->
                                calendar.set(y, m, d, h, min)
                                selectedDateTime = calendar.timeInMillis
                            },
                            calendar.get(java.util.Calendar.HOUR_OF_DAY),
                            calendar.get(java.util.Calendar.MINUTE),
                            true
                        )
                        timePicker.show()

                    },
                    calendar.get(java.util.Calendar.YEAR),
                    calendar.get(java.util.Calendar.MONTH),
                    calendar.get(java.util.Calendar.DAY_OF_MONTH)
                )

                datePicker.show()


            }) {
                Text("Pick Date & Time")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {

                when {
                    title.isBlank() -> {
                        scope.launch {
                            snackBarHostState.showSnackbar("Title cannot be empty")
                        }
                        return@Button
                    }

                    selectedDateTime < System.currentTimeMillis() -> {
                        scope.launch {
                            snackBarHostState.showSnackbar("Cannot select past date")
                        }
                        return@Button
                    }
                }

                if (existingEvent != null) {
                    viewModel.updateEvent(
                        existingEvent.copy(
                            title = title,
                            category = category,
                            location = location,
                            dateTime = selectedDateTime
                        )
                    )
                } else {
                    viewModel.addEvent(
                        Event(
                            title = title,
                            category = category,
                            location = location,
                            dateTime = selectedDateTime
                        )
                    )
                }

                onSave()

            }) {
                Text(if (existingEvent != null) "Update Event" else "Save Event")
            }
        }

    }

}
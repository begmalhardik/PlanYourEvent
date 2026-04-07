package com.example.planyourevent.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planyourevent.data.local.Event
import com.example.planyourevent.viewmodel.EventViewModel

@Composable
fun AddEditEventScreen(
    viewModel: EventViewModel,
    onSave: () -> Boolean
) {

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    val currentTime = System.currentTimeMillis() // simple timestamp

    Column(modifier = Modifier.padding(16.dp)) {

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
            // validation
            if(title.isEmpty())
                return@Button

            val event = Event(
                title = title,
                category = category,
                location = location,
                dateTime = currentTime
            )

            viewModel.addEvent(event)
            onSave()
        }) {
            Text("Save Event")
        }
    }
}
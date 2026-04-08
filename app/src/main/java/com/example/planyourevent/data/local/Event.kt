package com.example.planyourevent.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val category: String,
    val location: String,
    val dateTime: Long
)
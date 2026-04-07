package com.example.planyourevent.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao { // Data Access Object - we can write cleaner, maintainable and safer code with the help of DAO
    // it acts as a bridge between the Kotlin code and SQL

    @Insert(onConflict = OnConflictStrategy.REPLACE) // if we insert an element with same Primary key,
    // overwrote old data, creates a update like behaviour
    suspend fun insert(event: Event) // insert a new event in the DB
    // suspend because - DB operations are slow (I/O) so we are running them in background (coroutines)

    @Update
    suspend fun update(event: Event) // it uses PRIMARY KEY to undate a event, if not found, nothing happens

    @Delete
    suspend fun delete(event: Event) // deletes that event from the DB, based on PRIMARY KEY

    @Query("SELECT * FROM events ORDER BY dateTime ASC")
    fun getAllEvents(): Flow<List<Event>> // Fetches all events from DB
    // sorted by dateTime (ascending order)
    // automatically emits new data whenever DB changes
}
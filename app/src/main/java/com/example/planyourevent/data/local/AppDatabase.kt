package com.example.planyourevent.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Event::class], // our database will have a table based on the Event class
    version = 1,               // version of database (important for migration later)
    exportSchema = false       // ignore schema export (used in production / debugging)
)
abstract class AppDatabase : RoomDatabase() { // tells this is a database container

    abstract fun eventDao(): EventDao // this connects
                                      // Database -> DAO -> Queries
    // so the flow becomes, "UI -> viewModel -> Repository -> DAO -> Database"

    companion object { // static - we use companion object to implement Singleton in Kotlin

        @Volatile
        private var INSTANCE: AppDatabase? = null
        // SINGLETON - as we just need ONLY ONE database instance in the whole app

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) { // without synchronized, we will face MULTI-THREAD issue, ... two threads run at same time

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "event_database"
                    )
                    .fallbackToDestructiveMigration() // we do not need this line for the production as it will lose ALL user data on update
                    .build()

                INSTANCE = instance // next time, reuse it
                instance
            } // creates actual database file in device storage, with name "event_database"
        }
    }
} // this class represents the entire local database.
// It tells Room:
//  1) what tables exists    -> Event
//  2) what operation exists -> EventDao
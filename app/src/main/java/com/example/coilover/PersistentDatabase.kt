package com.example.coilover

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Event::class], version = 1, exportSchema = false)
@TypeConverters(EventTypeConverters::class)
abstract class PersistentDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao


    companion object {
        private val dbName: String = "coilover-db"
        @Volatile
        private var instance: PersistentDatabase? = null

        fun getInstance(context: Context): PersistentDatabase {
            return instance ?: synchronized(this) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersistentDatabase::class.java, dbName).build()
                instance
            }!!
        }
    }

    private class EventDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            instance?.let { database ->
                scope.launch(Dispatchers.IO) {
                    database.eventDao().deleteAll()
                }
            }
        }
    }
}
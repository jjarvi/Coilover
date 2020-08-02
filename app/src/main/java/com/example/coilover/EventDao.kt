package com.example.coilover

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface EventDao {

    @Query("SELECT * from events")
    fun getAll(): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(event: Event)

    @Query("DELETE from events")
    suspend fun deleteAll()
}
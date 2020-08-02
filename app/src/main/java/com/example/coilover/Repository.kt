package com.example.coilover

import androidx.lifecycle.LiveData


class Repository(private val dao: EventDao) {

    val events: LiveData<List<Event>> = dao.getAll()

    suspend fun insert(event: Event) {
        dao.insert(event)
    }
}
package com.example.coilover.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.coilover.Event
import com.example.coilover.PersistentDatabase
import com.example.coilover.Repository


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository =
        Repository(PersistentDatabase.getInstance(application).eventDao())
    val events: LiveData<List<Event>>

    init {
        events = repository.events
    }
}
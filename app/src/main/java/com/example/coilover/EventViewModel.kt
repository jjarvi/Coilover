package com.example.coilover

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    val events: LiveData<List<Event>>

    init {
        repository = Repository(PersistentDatabase.getInstance(application).eventDao())
        events = repository.events
    }

    fun insert(event: Event) = viewModelScope.launch{
        repository.insert(event)
    }
}
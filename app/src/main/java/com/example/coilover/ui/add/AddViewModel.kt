package com.example.coilover.ui.add

import android.app.Application
import androidx.lifecycle.*
import com.example.coilover.Event
import com.example.coilover.PersistentDatabase
import com.example.coilover.Repository
import kotlinx.coroutines.launch

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository =
        Repository(PersistentDatabase.getInstance(application).eventDao())

    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }
}
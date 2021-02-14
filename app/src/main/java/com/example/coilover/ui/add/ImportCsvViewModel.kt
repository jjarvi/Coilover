package com.example.coilover.ui.add

import android.app.Application
import androidx.lifecycle.*
import com.example.coilover.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream


class ImportCsvViewModel(application: Application) : AndroidViewModel(application) {

    val events = MutableLiveData<List<Event>>()
    val errors = MutableLiveData<List<Pair<EventCsvParser.Status, String>>>()
    val ready = MutableLiveData(false)

    private val repository: Repository =
        Repository(PersistentDatabase.getInstance(application).eventDao())

    fun import(input: InputStream) {
        events.value = emptyList()
        errors.value = emptyList()
        ready.value = false

        viewModelScope.launch(Dispatchers.IO) {
            val imported = arrayListOf<Event>()
            val failed = arrayListOf<Pair<EventCsvParser.Status, String>>()

            input.bufferedReader().forEachLine {
                val result = EventCsvParser.parse(it)
                when (result.status) {
                    EventCsvParser.Status.Ok ->
                    imported.add(result.event)
                    else -> failed.add(Pair(result.status, it))
                }
            }
            events.postValue(imported)
            errors.postValue(failed)
            ready.postValue(true)
        }
    }

}
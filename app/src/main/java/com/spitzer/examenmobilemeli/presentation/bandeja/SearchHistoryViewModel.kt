package com.spitzer.examenmobilemeli.presentation.bandeja

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.models.SearchHistory
import com.spitzer.examenmobilemeli.utils.Event

class SearchHistoryViewModel : ViewModel() {

    lateinit var searchHistory: SearchHistory

    private val searchString = MutableLiveData<Event<String>>()
    val search: LiveData<Event<String>> get() = searchString
    fun setSearchString(text: String) {
        searchString.value = Event(text)
    }
}

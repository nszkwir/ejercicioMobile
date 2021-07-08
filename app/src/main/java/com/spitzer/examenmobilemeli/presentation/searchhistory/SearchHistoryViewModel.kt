package com.spitzer.examenmobilemeli.presentation.searchhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.models.SearchHistory
import com.spitzer.examenmobilemeli.repository.ISearchHistoryRepository
import com.spitzer.examenmobilemeli.utils.Event

class SearchHistoryViewModel(
    private val repository: ISearchHistoryRepository
) : ViewModel() {

    lateinit var searchHistory: SearchHistory

    init {
        obtainSearchHistory()
    }

    private val searchString = MutableLiveData<Event<String>>()
    val search: LiveData<Event<String>> get() = searchString

    fun setSearchString(text: String) {
        searchString.value = Event(text)
    }

    fun updateSearchHistory(text: String) {
        if (searchHistory.busqueda_string.none { it == text }) {
            searchHistory.busqueda_string.add(text)
            repository.updateSearchHistory(searchHistory)
        }
    }

    fun obtainSearchHistory() {
        searchHistory = repository.getSearchHistory()
    }
}

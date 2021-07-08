package com.spitzer.examenmobilemeli.presentation.searchhistory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spitzer.examenmobilemeli.repository.SearchHistoryRepository

class SearchHistoryViewModelFactory(val context: Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelo: Class<T>): T {
        return SearchHistoryViewModel(
            SearchHistoryRepository(context)
        ) as T
    }
}

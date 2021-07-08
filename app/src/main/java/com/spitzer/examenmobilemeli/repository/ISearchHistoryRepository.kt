package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.models.SearchHistory

interface ISearchHistoryRepository {
    fun getSearchHistory(): SearchHistory
    fun updateSearchHistory(searchHistory: SearchHistory): Boolean
}
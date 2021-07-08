package com.spitzer.examenmobilemeli.repository

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.spitzer.examenmobilemeli.models.SearchHistory
import com.spitzer.examenmobilemeli.utils.AppConstants

class SearchHistoryRepository(private val context: Context) : ISearchHistoryRepository {
    private val gSON = GsonBuilder().create()

    override fun getSearchHistory(): SearchHistory {
        val preferences = context
            .getSharedPreferences(AppConstants.GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            ?: return SearchHistory()
        val serializedHistory: String
        return try {
            serializedHistory =
                preferences.getString(AppConstants.SEARCH_HISTORY_KEY, "") ?: ""
            gSON.fromJson(serializedHistory, SearchHistory::class.java)
        } catch (e: Exception) {
            Log.e(
                AppConstants.ETAG_SHARED_PREFERENCES,
                e.localizedMessage
                    ?: "Excepcion al obtener el historial de búsqueda en Shared Preferences Globales."
            )
            SearchHistory()
        }
    }

    override fun updateSearchHistory(searchHistory: SearchHistory): Boolean {
        val serializedHistory = gSON.toJson(searchHistory)
        val preferences = context.getSharedPreferences(
            AppConstants.GLOBAL_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        ) ?: return false
        return try {
            with(preferences.edit()) {
                putString(
                    AppConstants.SEARCH_HISTORY_KEY,
                    serializedHistory
                )
                commit()
            }
            true
        } catch (e: Exception) {
            Log.e(
                AppConstants.ETAG_SHARED_PREFERENCES,
                e.localizedMessage
                    ?: "Excepcion al actualizar el historial de búsqueda en Shared Preferences Globales."
            )
            false
        }
    }
}

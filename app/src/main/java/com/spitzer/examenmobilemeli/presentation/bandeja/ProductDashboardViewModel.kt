package com.spitzer.examenmobilemeli.presentation.bandeja

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.data.ProductSearch
import com.spitzer.examenmobilemeli.repository.ProductRepository
import com.spitzer.examenmobilemeli.utils.Event
import com.spitzer.network.Estado
import com.spitzer.network.ResultData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProductDashboardViewModel(
    override val coroutineContext: CoroutineContext,
    private val repository: ProductRepository = ProductRepository()
) : ViewModel(), CoroutineScope {

    var searchText: String = ""
    var searchResults: ProductSearch = ProductSearch()

    private val searchResponseState = MutableLiveData<Event<Estado>>()
    val searchResponse: LiveData<Event<Estado>> get() = searchResponseState

    fun searchProducts(query: String) {
        searchText = query
        searchResponseState.value = Event(Estado.CARGANDO)
        this.getProducts(query)
    }

    private fun getProducts(query: String) = launch {
        when (val result = repository.getProduct(query)) {
            is ResultData.Success -> {
                if (result.data != null) {
                    searchResults = result.data ?: ProductSearch()
                    searchResponseState.value = Event(Estado.EXITO)
                } else {
                    searchResults = ProductSearch()
                    searchResponseState.value = Event(Estado.ERROR)
                }
            }
            is ResultData.Error -> {
                searchResponseState.value = Event(Estado.SIN_CONEXION_INTERNET)
            }
        }
    }
}

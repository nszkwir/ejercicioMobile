package com.spitzer.examenmobilemeli.presentation.bandeja

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.data.ProductSearch
import com.spitzer.examenmobilemeli.repository.IProductRepository
import com.spitzer.examenmobilemeli.repository.ProductRepository
import com.spitzer.examenmobilemeli.utils.Event
import com.spitzer.network.ViewState
import com.spitzer.network.ResultData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProductDashboardViewModel(
    override val coroutineContext: CoroutineContext,
    private val repository: IProductRepository = ProductRepository()
) : ViewModel(), CoroutineScope {

    var searchText: String = ""
    var searchResults: ProductSearch = ProductSearch()

    private val searchResponseState = MutableLiveData<Event<ViewState>>()
    val searchResponse: LiveData<Event<ViewState>> get() = searchResponseState

    fun searchProducts(query: String) {
        searchText = query
        searchResponseState.value = Event(ViewState.CARGANDO)
        this.getProducts(query)
    }

    private fun getProducts(query: String) = launch {
        when (val result = repository.getProduct(query)) {
            is ResultData.Success -> {
                if (result.data != null) {
                    searchResults = result.data ?: ProductSearch()
                    searchResponseState.value = Event(ViewState.EXITO)
                } else {
                    searchResults = ProductSearch()
                    searchResponseState.value = Event(ViewState.ERROR)
                }
            }
            is ResultData.Error -> {
                if (result.isNetworkError()) {
                    searchResponseState.value = Event(ViewState.SIN_CONEXION_INTERNET)
                } else {
                    searchResponseState.value = Event(ViewState.ERROR)
                }
            }
        }
    }
}

package com.spitzer.examenmobilemeli.presentation.bandeja

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.data.BusquedaArticulos
import com.spitzer.examenmobilemeli.repository.ProductoRepository
import com.spitzer.examenmobilemeli.utils.Event
import com.spitzer.network.Estado
import com.spitzer.network.ResultData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BandejaProductosViewModel(
    override val coroutineContext: CoroutineContext,
    private val repository: ProductoRepository = ProductoRepository()
) : ViewModel(), CoroutineScope {

    var textoBusqueda: String = ""
    var resultadoBusqueda: BusquedaArticulos = BusquedaArticulos()

    private val estadoRespuestaProductos = MutableLiveData<Event<Estado>>()
    val respuestaProductos: LiveData<Event<Estado>> get() = estadoRespuestaProductos

    fun buscarProductos(query: String) {
        textoBusqueda = query
        estadoRespuestaProductos.value = Event(Estado.CARGANDO)
        this.getProductos(query)
    }

    private fun getProductos(query: String) = launch {
        when (val result = repository.getProduct(query)) {
            is ResultData.Success -> {
                if (result.data != null) {
                    resultadoBusqueda = result.data ?: BusquedaArticulos()
                    estadoRespuestaProductos.value = Event(Estado.EXITO)
                } else {
                    resultadoBusqueda = BusquedaArticulos()
                    estadoRespuestaProductos.value = Event(Estado.ERROR)
                }
            }
            is ResultData.Error -> {
                estadoRespuestaProductos.value = Event(Estado.SIN_CONEXION_INTERNET)
            }
        }
    }
}

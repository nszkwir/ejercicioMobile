package com.spitzer.examenmobilemeli.presentation.bandeja

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.models.HistorialBusqueda
import com.spitzer.examenmobilemeli.utils.Event

class HistorialBusquedaViewModel : ViewModel() {

    lateinit var historialBusqueda: HistorialBusqueda

    private val searchString = MutableLiveData<Event<String>>()
    val busqueda: LiveData<Event<String>> get() = searchString
    fun setBusqueda(texto: String) { searchString.value = Event(texto) }

}
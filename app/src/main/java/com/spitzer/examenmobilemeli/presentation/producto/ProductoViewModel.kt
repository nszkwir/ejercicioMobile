package com.spitzer.examenmobilemeli.presentation.producto

import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.data.Result

class ProductoViewModel : ViewModel() {
    var producto: Result = Result()
    fun getProductInfo(): String {
        return producto.attributes.map{ "${it.name}: ${it.valueName}"}.joinToString("\n")
    }
}
package com.spitzer.examenmobilemeli.presentation.bandeja

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spitzer.examenmobilemeli.repository.IProductoService
import kotlinx.coroutines.Dispatchers

class BandejaProductosViewModelFactory(): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelo: Class<T>): T {

        return BandejaProductosViewModel(Dispatchers.Main) as T
    }

}
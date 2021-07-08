package com.spitzer.examenmobilemeli.presentation.bandeja

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spitzer.examenmobilemeli.repository.ProductRepository
import kotlinx.coroutines.Dispatchers

class ProductDashboardViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelo: Class<T>): T {
        return ProductDashboardViewModel(
            Dispatchers.Main,
            ProductRepository()
        ) as T
    }
}

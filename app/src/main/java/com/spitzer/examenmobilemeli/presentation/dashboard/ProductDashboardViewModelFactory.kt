package com.spitzer.examenmobilemeli.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spitzer.examenmobilemeli.repository.ProductRepository
import kotlinx.coroutines.Dispatchers

@SuppressWarnings("UNCHECKED_CAST")
class ProductDashboardViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelo: Class<T>): T {
        return ProductDashboardViewModel(
            Dispatchers.Main,
            ProductRepository()
        ) as T
    }
}

package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.data.BusquedaArticulos
import com.spitzer.examenmobilemeli.utils.safeCall
import com.spitzer.network.ApiClient
import com.spitzer.network.ResultData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductoRepository(
    private val service: IProductoService = ApiClient().createService(IProductoService::class.java),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getProduct(queryString: String): ResultData<BusquedaArticulos?> {
        return withContext(dispatcher) {
            return@withContext safeCall { service.getProductos(queryString) }
        }
    }
}

package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.data.ProductSearch
import com.spitzer.examenmobilemeli.utils.safeCall
import com.spitzer.network.ApiClient
import com.spitzer.network.ResultData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val service: IProductService = ApiClient().createService(IProductService::class.java),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IProductRepository {
    override suspend fun getProduct(queryString: String): ResultData<ProductSearch?> {
        return withContext(dispatcher) {
            return@withContext safeCall { service.getProducts(queryString) }
        }
    }
}

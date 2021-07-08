package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.data.ProductSearch
import com.spitzer.network.ResultData

interface IProductRepository {
    suspend fun getProduct(queryString: String): ResultData<ProductSearch?>
}
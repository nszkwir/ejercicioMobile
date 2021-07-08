package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.data.ProductSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IProductService {
    @GET("search")
    suspend fun getProducts(@Query("q") searchQuery: String): Response<ProductSearch>
}

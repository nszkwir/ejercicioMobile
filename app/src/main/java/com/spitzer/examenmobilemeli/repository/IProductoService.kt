package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.data.BusquedaArticulos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IProductoService {
    @GET("search")
    suspend fun getProductos(@Query("q") queryProducto: String): Response<BusquedaArticulos>
}
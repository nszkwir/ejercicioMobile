package com.spitzer.examenmobilemeli.repository

import androidx.lifecycle.LiveData
import com.spitzer.examenmobilemeli.data.BusquedaArticulos
import retrofit2.Response
import retrofit2.http.*

interface IProductoService {
    @GET("search")
    suspend fun getProductos(@Query("q") queryProducto: String): Response<BusquedaArticulos>
}
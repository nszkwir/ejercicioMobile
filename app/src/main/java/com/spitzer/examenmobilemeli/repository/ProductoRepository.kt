package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.data.BusquedaArticulos
import com.spitzer.network.Resultado
import com.spitzer.network.ApiClient
import retrofit2.Response
import java.lang.Exception

class ProductoRepository() {
    var mProductoService: IProductoService = ApiClient().createService(IProductoService::class.java)

    suspend fun getProduct(queryString: String): Resultado<Exception, Response<BusquedaArticulos>> {
        return try {
            Resultado.build { mProductoService.getProductos(queryString) ?: throw Exception()}
        } catch(e: Exception) {
            Resultado.build(throw e)
        }
    }

}

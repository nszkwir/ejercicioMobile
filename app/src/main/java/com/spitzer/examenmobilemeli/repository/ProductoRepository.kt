package com.spitzer.examenmobilemeli.repository

import com.spitzer.examenmobilemeli.data.BusquedaArticulos
import com.spitzer.network.ApiClient
import retrofit2.Response

class ProductoRepository() {
    var mProductoService: IProductoService = ApiClient().createService(IProductoService::class.java)

    suspend fun getProduct(queryString: String): Response<BusquedaArticulos> {
        return mProductoService.getProductos(queryString)
    }

}

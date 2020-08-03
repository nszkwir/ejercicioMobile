package com.spitzer.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private var gson: Gson? = null
    private var retrofit: Retrofit? = null
    //private var accesstoken: String? = null

    constructor() {
        this.gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
        //this.accesstoken = "123asd"
        this.ConfigurarServicios()
    }

    private fun ConfigurarServicios() {

        val builder = OkHttpClient.Builder()

        val client = builder
            .readTimeout(50, TimeUnit.SECONDS)
            .connectTimeout(50, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(getURLBase())
            .addConverterFactory(GsonConverterFactory.create(this.gson!!))
            .client(client)
            .build()

    }

    fun getURLBase():String{
        return "https://api.mercadolibre.com/sites/MLA/"
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit!!.create(serviceClass)
    }
}
package com.spitzer.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private var gson: Gson? = null
    private var retrofit: Retrofit? = null
    //private var accesstoken: String? = null

    init {
        this.gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
        this.configureServices()
    }

    private fun configureServices() {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = builder
            .readTimeout(50, TimeUnit.SECONDS)
            .connectTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(getBaseURL())
            .addConverterFactory(GsonConverterFactory.create(this.gson!!))
            .client(client)
            .build()
    }

    private fun getBaseURL(): String {
        return "https://api.mercadolibre.com/sites/MLA/"
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit!!.create(serviceClass)
    }
}

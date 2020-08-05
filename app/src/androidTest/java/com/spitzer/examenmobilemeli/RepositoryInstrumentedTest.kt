package com.spitzer.examenmobilemeli

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.spitzer.network.ApiClient
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryInstrumentedTest {

    @Test
    fun createApiClient() {
        val apiClient = ApiClient()
        Assert.assertNotNull(apiClient)
    }

    @Test(expected = IllegalArgumentException::class)
    fun retrofitClientWrongParameters() {
        val apiClient = ApiClient().createService(Object::class.java)
    }
}
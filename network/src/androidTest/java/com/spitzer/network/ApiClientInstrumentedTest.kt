package com.spitzer.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.net.InterfaceAddress
import java.util.*

@RunWith(AndroidJUnit4::class)
class ApiClientInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.spitzer.network.test", appContext.packageName)
    }

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
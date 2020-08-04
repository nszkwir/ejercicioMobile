package com.spitzer.examenmobilemeli

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spitzer.examenmobilemeli.presentation.bandeja.BandejaProductosFragment
import com.spitzer.examenmobilemeli.utils.AppConstants.ETAG_TEST
import com.spitzer.network.Estado
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BandejaFragmentInstrumentedTest {

    @Test(expected = ClassCastException::class)
    fun handleResponseBusquedaWrongEstadoTest() {
        val fragment = BandejaProductosFragment()
        fragment.handleResponseBusquedaProducto("CUALQUIERA" as Estado)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun handleResponseBusquedaTest() {
        val fragment = BandejaProductosFragment()
        try {fragment.handleResponseBusquedaProducto(Estado.ERROR) }
        catch (e: UninitializedPropertyAccessException) {
            Log.e(ETAG_TEST,e.message + "\n" + e.stackTrace)
            throw e
        }
    }

}
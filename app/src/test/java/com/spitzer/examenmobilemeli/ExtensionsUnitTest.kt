package com.spitzer.examenmobilemeli

import com.spitzer.examenmobilemeli.utils.toCash
import org.junit.Assert
import org.junit.Test

class ExtensionsUnitTest {

    @Test
    fun toCash_isCorrect() {
        val price: Double = 22351.25
        Assert.assertNotNull("", price.toCash())
    }
}
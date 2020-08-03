package com.spitzer.network

sealed class Resultado<out E, out V> {

    data class Value<out V>(val value: V) : Resultado<Nothing, V>()
    data class Error<out E>(val error: E) : Resultado<E, Nothing>()

    companion object Factory{
        inline fun <V> build(function: () -> V): Resultado<Exception, V> =
            try {
                Value(function.invoke())
            } catch (e: java.lang.Exception) {
                Error(e)
            }
    }

}
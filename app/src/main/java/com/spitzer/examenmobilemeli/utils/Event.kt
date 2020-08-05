package com.spitzer.examenmobilemeli.utils

open class Event<out T>(private val content: T? = null) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }

    fun peekContent(): T? = content
}

class VoidEvent {
    private var hasBeenHandled = false

    fun hasBeenHandled(): Boolean = if (hasBeenHandled) {
        true
    } else {
        hasBeenHandled = true
        false
    }
}
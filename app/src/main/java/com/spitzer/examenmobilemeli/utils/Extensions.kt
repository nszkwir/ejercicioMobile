package com.spitzer.examenmobilemeli.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*

internal fun SearchView.showkeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

internal fun SearchView.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

internal fun <T> LiveData<out Event<T>>.observeEvent(
    owner: LifecycleOwner,
    onEventUnhandled: (T) -> Unit
) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandled) })
}

internal fun LiveData<out VoidEvent>.observeEvent(
    owner: LifecycleOwner,
    onEventUnhandled: () -> Unit
) {
    observe(owner, Observer { if (!it.hasBeenHandled()) onEventUnhandled() })
}

internal fun Double.toCash(): String = NumberFormat.getNumberInstance(Locale.ITALY).format(this)

internal val String.Companion.emptyString: String
    get() = ""

fun <T : RecyclerView.ViewHolder> T.listenToClick(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}
package com.spitzer.examenmobilemeli.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SearchHistory(
    @SerializedName("busqueda_string")
    var busqueda_string: ArrayList<String>
) : Serializable {
    constructor() : this(arrayListOf())
}

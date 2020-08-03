package com.spitzer.examenmobilemeli.data

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = ""
)
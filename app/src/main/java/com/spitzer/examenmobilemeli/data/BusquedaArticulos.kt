package com.spitzer.examenmobilemeli.data

import com.google.gson.annotations.SerializedName

data class BusquedaArticulos(
    @SerializedName("paging")
    var paging: Paging = Paging(),
    @SerializedName("query")
    var query: String = "",
    @SerializedName("results")
    var results: ArrayList<Result> = arrayListOf(),
    @SerializedName("site_id")
    var siteId: String = ""
)
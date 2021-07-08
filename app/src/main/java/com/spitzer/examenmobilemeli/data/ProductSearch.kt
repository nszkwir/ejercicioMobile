package com.spitzer.examenmobilemeli.data

import com.google.gson.annotations.SerializedName

data class ProductSearch(
    @SerializedName("paging")
    var paging: Paging = Paging(),
    @SerializedName("query")
    var query: String = "",
    @SerializedName("results")
    var results: ArrayList<Product> = arrayListOf(),
    @SerializedName("site_id")
    var siteId: String = ""
)
package com.spitzer.examenmobilemeli.data

import com.google.gson.annotations.SerializedName

data class Paging(
    @SerializedName("limit")
    var limit: Int = 0,
    @SerializedName("offset")
    var offset: Int = 0,
    @SerializedName("primary_results")
    var primaryResults: Int = 0,
    @SerializedName("total")
    var total: Int = 0
)
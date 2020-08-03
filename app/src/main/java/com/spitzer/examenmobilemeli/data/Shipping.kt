package com.spitzer.examenmobilemeli.data

import com.google.gson.annotations.SerializedName


data class Shipping(
    @SerializedName("free_shipping")
    var freeShipping: Boolean = false,
    @SerializedName("logistic_type")
    var logisticType: String = "",
    @SerializedName("mode")
    var mode: String = "",
    @SerializedName("store_pick_up")
    var storePickUp: Boolean = false,
    @SerializedName("tags")
    var tags: List<String> = listOf()
)
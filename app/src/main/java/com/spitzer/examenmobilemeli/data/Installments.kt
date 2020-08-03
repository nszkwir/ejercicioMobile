package com.spitzer.examenmobilemeli.data

import com.google.gson.annotations.SerializedName


data class Installments(
    @SerializedName("amount")
    var amount: Double = 0.0,
    @SerializedName("currency_id")
    var currencyId: String = "",
    @SerializedName("quantity")
    var quantity: Int = 0,
    @SerializedName("rate")
    var rate: Double = 0.0
)
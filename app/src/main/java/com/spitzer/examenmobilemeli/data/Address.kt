package com.spitzer.examenmobilemeli.data

import com.google.gson.annotations.SerializedName


data class Address(
    @SerializedName("city_id")
    var cityId: String? = null,
    @SerializedName("city_name")
    var cityName: String = "",
    @SerializedName("state_id")
    var stateId: String = "",
    @SerializedName("state_name")
    var stateName: String = ""
)
package com.spitzer.examenmobilemeli.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("city_id")
    var cityId: String? = null,
    @SerializedName("city_name")
    var cityName: String = "",
    @SerializedName("state_id")
    var stateId: String = "",
    @SerializedName("state_name")
    var stateName: String = ""
) : Parcelable
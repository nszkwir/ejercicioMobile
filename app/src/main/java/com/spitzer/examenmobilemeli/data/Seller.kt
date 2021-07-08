package com.spitzer.examenmobilemeli.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seller(
    @SerializedName("car_dealer")
    var carDealer: Boolean = false,
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("power_seller_status")
    var powerSellerStatus: String = "",
    @SerializedName("real_estate_agency")
    var realEstateAgency: Boolean = false,
    @SerializedName("tags")
    var tags: List<String>? = null
) : Parcelable

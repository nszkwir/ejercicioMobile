package com.spitzer.examenmobilemeli.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerAddress(
    @SerializedName("address_line")
    var addressLine: String = "",
    @SerializedName("city")
    var city: City = City(),
    @SerializedName("comment")
    var comment: String = "",
    @SerializedName("country")
    var country: Country = Country(),
    @SerializedName("id")
    var id: String = "",
    @SerializedName("latitude")
    var latitude: String = "",
    @SerializedName("longitude")
    var longitude: String = "",
    @SerializedName("state")
    var state: State = State(),
    @SerializedName("zip_code")
    var zipCode: String = ""
) : Parcelable

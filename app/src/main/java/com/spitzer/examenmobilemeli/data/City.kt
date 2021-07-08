package com.spitzer.examenmobilemeli.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String = ""
) : Parcelable
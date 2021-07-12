package com.spitzer.examenmobilemeli.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = ""
) : Parcelable

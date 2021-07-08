package com.spitzer.examenmobilemeli.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ValueStruct (
    @SerializedName("number")
    var number: Double? = 0.0,
    @SerializedName("unit")
    var unit: String? = ""
) : Parcelable
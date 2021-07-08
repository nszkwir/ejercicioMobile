package com.spitzer.examenmobilemeli.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attribute(
    @SerializedName("attribute_group_id")
    var attributeGroupId: String = "",
    @SerializedName("attribute_group_name")
    var attributeGroupName: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("source")
    var source: Long = 0,
    @SerializedName("value_id")
    var valueId: String = "",
    @SerializedName("value_name")
    var valueName: String = "",
    @SerializedName("value_struct")
    var valueStruct: String? = null
) : Parcelable
package com.spitzer.examenmobilemeli.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("accepts_mercadopago")
    var acceptsMercadopago: Boolean = false,
    @SerializedName("address")
    var address: Address = Address(),
    @SerializedName("attributes")
    var attributes: List<Attribute> = listOf(),
    @SerializedName("available_quantity")
    var availableQuantity: Int = 0,
    @SerializedName("buying_mode")
    var buyingMode: String = "",
    @SerializedName("catalog_listing")
    var catalogListing: Boolean = false,
    @SerializedName("catalog_product_id")
    var catalogProductId: String = "",
    @SerializedName("category_id")
    var categoryId: String = "",
    @SerializedName("condition")
    var condition: String = "",
    @SerializedName("currency_id")
    var currencyId: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("installments")
    var installments: Installments = Installments(),
    @SerializedName("listing_type_id")
    var listingTypeId: String = "",
    @SerializedName("official_store_id")
    var officialStoreId: Int? = null,
    @SerializedName("original_price")
    var originalPrice: Any? = null,
    @SerializedName("permalink")
    var permalink: String = "",
    @SerializedName("price")
    var price: Double = 0.0,
    @SerializedName("seller")
    var seller: Seller = Seller(),
    @SerializedName("seller_address")
    var sellerAddress: SellerAddress = SellerAddress(),
    @SerializedName("shipping")
    var shipping: Shipping = Shipping(),
    @SerializedName("site_id")
    var siteId: String = "",
    @SerializedName("sold_quantity")
    var soldQuantity: Int = 0,
    @SerializedName("stop_time")
    var stopTime: String = "",
    @SerializedName("tags")
    var tags: List<String> = listOf(),
    @SerializedName("thumbnail")
    var thumbnail: String = "",
    @SerializedName("title")
    var title: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        TODO("address"),
        TODO("attributes"),
        parcel.readInt(),
        parcel.readString()?:"",
        parcel.readByte() != 0.toByte(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        TODO("installments"),
        parcel.readString()?:"",
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("originalPrice"),
        parcel.readString()?:"",
        parcel.readDouble(),
        TODO("seller"),
        TODO("sellerAddress"),
        TODO("shipping"),
        parcel.readString()?:"",
        parcel.readInt(),
        parcel.readString()?:"",
        parcel.createStringArrayList()?: arrayListOf(),
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (acceptsMercadopago) 1 else 0)
        parcel.writeInt(availableQuantity)
        parcel.writeString(buyingMode)
        parcel.writeByte(if (catalogListing) 1 else 0)
        parcel.writeString(catalogProductId)
        parcel.writeString(categoryId)
        parcel.writeString(condition)
        parcel.writeString(currencyId)
        parcel.writeString(id)
        parcel.writeString(listingTypeId)
        parcel.writeValue(officialStoreId)
        parcel.writeString(permalink)
        parcel.writeDouble(price)
        parcel.writeString(siteId)
        parcel.writeInt(soldQuantity)
        parcel.writeString(stopTime)
        parcel.writeStringList(tags)
        parcel.writeString(thumbnail)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}
package com.spitzer.examenmobilemeli.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class Product(
    @SerializedName("accepts_mercadopago")
    var acceptsMercadopago: Boolean = false,
    @SerializedName("address")
    var address: Address = Address(),
    @SerializedName("attributes")
    var attributes: List<Attribute> = listOf(),
    @SerializedName("available_quantity")
    var availableQuantity: Int = 0,
    @SerializedName("buying_mode")
    var buyingMode: String? = "",
    @SerializedName("catalog_listing")
    var catalogListing: Boolean = false,
    @SerializedName("catalog_product_id")
    var catalogProductId: String? = "",
    @SerializedName("category_id")
    var categoryId: String? = "",
    @SerializedName("condition")
    var condition: String? = "",
    @SerializedName("currency_id")
    var currencyId: String? = "",
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("installments")
    var installments: Installments = Installments(),
    @SerializedName("listing_type_id")
    var listingTypeId: String? = "",
    @SerializedName("official_store_id")
    var officialStoreId: Int? = null,
    @SerializedName("original_price")
    var originalPrice: String? = null,
    @SerializedName("permalink")
    var permalink: String? = "",
    @SerializedName("price")
    var price: Double = 0.0,
    @SerializedName("seller")
    var seller: Seller = Seller(),
    @SerializedName("seller_address")
    var sellerAddress: SellerAddress = SellerAddress(),
    @SerializedName("shipping")
    var shipping: Shipping = Shipping(),
    @SerializedName("site_id")
    var siteId: String? = "",
    @SerializedName("sold_quantity")
    var soldQuantity: Int = 0,
    @SerializedName("stop_time")
    var stopTime: String? = "",
    @SerializedName("tags")
    var tags: ArrayList<String>? = arrayListOf(),
    @SerializedName("thumbnail")
    var thumbnail: String? = "",
    @SerializedName("title")
    var title: String? = ""
) : Parcelable
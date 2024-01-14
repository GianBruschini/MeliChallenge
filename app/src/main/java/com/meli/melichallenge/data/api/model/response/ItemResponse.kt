package com.meli.melichallenge.data.api.model.response

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("id") val itemId: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Float,
    @SerializedName("currency_id") val currencyId: String,
    @SerializedName("condition") val condition: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("pictures") val pictures: ArrayList<PictureResponse>,
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("seller_address") val sellerAddress: SellerAddressResponse,
    @SerializedName("attributes") val attributes: ArrayList<AttributeResponse>
)

data class PictureResponse(
    @SerializedName("id") val pictureId: String,
    @SerializedName("url") val url: String,
    @SerializedName("size") val size: String,
    @SerializedName("max_size") val maxSize: String
)

data class SellerAddressResponse(
    @SerializedName("city") val city: AddressProperty,
    @SerializedName("state") val state: AddressProperty
)

data class AddressProperty(
    @SerializedName("name") val name: String
)

data class AttributeResponse(
    @SerializedName("id") val attributeId: String,
    @SerializedName("name") val name: String,
    @SerializedName("value_name") val valueName: String?
)
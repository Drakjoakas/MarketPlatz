package com.drakjoakas.marketplatz.model

import com.google.gson.annotations.SerializedName

class ProductoDetail {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("imag_url")
    var image: String? = null

    @SerializedName("desc")
    var description: String? = null

}
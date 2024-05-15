package com.anirudh.findfalcone.data.entity

import com.google.gson.annotations.SerializedName

data class FindResponse(
    @SerializedName("planet_name")
    var planetName: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("error")
    var error: String? = null
)

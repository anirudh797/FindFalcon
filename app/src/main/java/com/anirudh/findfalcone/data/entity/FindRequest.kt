package com.anirudh.findfalcone.data.entity

import com.google.gson.annotations.SerializedName

data class FindRequest(

    @SerializedName("planet_names")
    var planetNames : ArrayList<String>,

    @SerializedName("vehicle_names")
    var vehicleNames : ArrayList<String>,

    @SerializedName("token")
    var token : String

)

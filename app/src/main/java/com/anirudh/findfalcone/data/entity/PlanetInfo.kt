package com.anirudh.findfalcone.data.entity

import com.google.gson.annotations.SerializedName

open class InfoItem(val nameInfo: String)

data class PlanetInfo(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("name")
    val name: String,
    val isSelected: Boolean = false
) : InfoItem(name)

data class VehicleInfo(
    @SerializedName("max_distance")
    val maxDistance: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("speed")
    val speed: Int,
    @SerializedName("total_no")
    val totalUnits: Int,
    var isSelected: Boolean = false
) : InfoItem(name)
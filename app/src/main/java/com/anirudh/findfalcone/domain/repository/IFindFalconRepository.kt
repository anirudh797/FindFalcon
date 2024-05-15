package com.anirudh.findfalcone.domain.repository

import com.anirudh.findfalcone.data.entity.FindResponse
import com.anirudh.findfalcone.data.entity.PlanetsList
import com.anirudh.findfalcone.data.entity.TokenInfo
import com.anirudh.findfalcone.data.entity.VehicleList

interface IFindFalconRepository {
    suspend fun getPlanets(
    ): PlanetsList

    suspend fun getVehicles(
    ): VehicleList

    suspend fun getToken(): TokenInfo

    suspend fun findFalcon(
        token: String,
        planetNamesList: ArrayList<String>,
        vehicleNamesList: ArrayList<String>
    ): FindResponse
}
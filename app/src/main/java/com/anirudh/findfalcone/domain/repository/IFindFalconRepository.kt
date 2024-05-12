package com.anirudh.findfalcone.domain.repository

import com.anirudh.findfalcone.data.entity.PlanetsList
import com.anirudh.findfalcone.data.entity.TokenInfo
import com.anirudh.findfalcone.data.entity.VehicleList

interface IFindFalconRepository {
    suspend fun getPlanets(
    ): PlanetsList

    suspend fun getVehicles(
    ): VehicleList

    suspend fun getToken(): TokenInfo
}
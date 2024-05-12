package com.anirudh.findfalcone.data.remote

import com.anirudh.findfalcone.data.entity.PlanetsList
import com.anirudh.findfalcone.data.entity.TokenInfo
import com.anirudh.findfalcone.data.entity.VehicleList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface FalconApi {

    @GET("planets")
    suspend fun getPlanets(
    ): Response<PlanetsList>

    @GET("vehicles")
    suspend fun getVehicles(
    ): Response<VehicleList>

    @POST("token")
    suspend fun getToken(): Response<TokenInfo>
}

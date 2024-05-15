package com.anirudh.findfalcone.data.remote

import com.anirudh.findfalcone.data.entity.FindRequest
import com.anirudh.findfalcone.data.entity.FindResponse
import com.anirudh.findfalcone.data.entity.PlanetsList
import com.anirudh.findfalcone.data.entity.TokenInfo
import com.anirudh.findfalcone.data.entity.VehicleList
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface FalconApi {

    @GET("planets")
    suspend fun getPlanets(
    ): Response<PlanetsList>

    @GET("vehicles")
    suspend fun getVehicles(
    ): Response<VehicleList>


    @POST("token")
    suspend fun getToken(
        @Header("Accept") contentType: String,
        @Body body: JSONObject
    ): Response<TokenInfo>


    @POST("find")
    suspend fun findFalcon(
        @Header("Accept") accept: String,
        @Header("Content-Type") contentType: String,
        @Body findRequest: FindRequest
    ): Response<FindResponse>


}

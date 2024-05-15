package com.anirudh.findfalcone.domain.repository

import android.util.Log
import com.anirudh.findfalcone.data.entity.FindRequest
import com.anirudh.findfalcone.data.entity.FindResponse
import com.anirudh.findfalcone.data.entity.PlanetsList
import com.anirudh.findfalcone.data.entity.TokenInfo
import com.anirudh.findfalcone.data.entity.VehicleList
import com.anirudh.findfalcone.data.remote.FalconApi
import com.anirudh.findfalcone.domain.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class FindFalconRepository @Inject constructor(
    private val falconApi: FalconApi,
) : IFindFalconRepository {

    override suspend fun getPlanets(): PlanetsList {
        return withContext(Dispatchers.IO) {
            falconApi.let {
                val result = it.getPlanets()
                if (result.isSuccessful) {
                    result.body() ?: PlanetsList()
                } else {
                    PlanetsList()
                }
            }
        }
    }

    override suspend fun getVehicles(): VehicleList {
        return withContext(Dispatchers.IO) {
            falconApi.let {
                val result = it.getVehicles()
                if (result.isSuccessful) {
                    result.body() ?: VehicleList()
                } else {
                    VehicleList()
                }
            }
        }
    }


    override suspend fun getToken(): TokenInfo {
        return withContext(Dispatchers.IO) {
            Log.d("Anirudh", "getToken")
            falconApi.let {
                val result = it.getToken(Constants.CONTENT_TYPE, JSONObject())
                if (result.isSuccessful) {
                    Log.d("Anirudh", "getToken ${result.body()}")
                    result.body() ?: TokenInfo("")
                } else {
                    TokenInfo("")
                }
            }
        }
    }

    override suspend fun findFalcon(
        token: String,
        planetNamesList: ArrayList<String>,
        vehicleNamesList: ArrayList<String>
    ): FindResponse {
        return withContext(Dispatchers.IO) {
            falconApi.let {
                val findRequest = FindRequest(
                    token = token,
                    planetNames = planetNamesList,
                    vehicleNames = vehicleNamesList
                )
                val result =
                    it.findFalcon(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE, findRequest)
                if (result.isSuccessful) {
                    result.body() ?: FindResponse()
                } else {
                    FindResponse()
                }
            }
        }
    }

}
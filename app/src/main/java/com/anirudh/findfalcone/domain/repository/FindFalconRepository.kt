package com.anirudh.findfalcone.domain.repository

import com.anirudh.findfalcone.data.entity.PlanetsList
import com.anirudh.findfalcone.data.entity.TokenInfo
import com.anirudh.findfalcone.data.entity.VehicleList
import com.anirudh.findfalcone.data.remote.FalconApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindFalconRepository @Inject constructor(
    private val falconApi: FalconApi,
) : IFindFalconRepository {


//    override suspend fun getProfiles(): List<ProfileInfo> {
//
//        val result = withContext(Dispatchers.IO) {
//            if (NetworkManager.isConnectionAvailable(app.applicationContext)) {
//                /* if network available,sync localDb with server */
//                try {
//                    val result = falconApi.getProfiles()
//                    if (result.isSuccessful) {
//                        val results = result.body()?.profileInfos
//                        saveInDb(results)
//                        results ?: emptyList()
//                    } else {
//                        /* if api fails ,try to fetch from Db */
//                        fetchFromDb()
//                    }
//                } catch (ex: Exception) {
//                    fetchFromDb()
//                }
//
//            } else {
//                /* if network not available,fetch Data from Db */
//                fetchFromDb()
//            }
//        }
//        return result
//    }


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
            falconApi.let {
                val result = it.getToken()
                if (result.isSuccessful) {
                    result.body() ?: TokenInfo("")
                } else {
                    TokenInfo("")
                }
            }
        }
    }

}
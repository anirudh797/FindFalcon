package com.anirudh.findfalcone.view.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anirudh.findfalcone.R
import com.anirudh.findfalcone.data.entity.InfoItem
import com.anirudh.findfalcone.data.entity.PlanetInfo
import com.anirudh.findfalcone.data.entity.VehicleInfo
import com.anirudh.findfalcone.data.remote.RetrofitInstance
import com.anirudh.findfalcone.domain.repository.FindFalconRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class FindFalconViewModel @Inject constructor(
    private val falconRepository: FindFalconRepository,
    application: Application
) :
    AndroidViewModel(application) {

    private var _loadingProgressLiveData = MutableLiveData(false)
    var loadingProgressLiveData: LiveData<Boolean> = _loadingProgressLiveData

    private var _showError = MutableLiveData(false)
    var showError: LiveData<Boolean> = _showError

    private var _vehiclesList = MutableLiveData<List<InfoItem>>()
    val vehicleList: LiveData<List<InfoItem>> = _vehiclesList

    private var _planetsList = MutableLiveData<List<InfoItem>>()
    val planetsList: LiveData<List<InfoItem>> = _planetsList

    private var _travelSummary = MutableLiveData<String?>()
    val travelSummary: LiveData<String?> = _travelSummary

    private var _findSuccess = MutableLiveData<Pair<String, Boolean>?>()
    val findSuccess: LiveData<Pair<String, Boolean>?> = _findSuccess

    private var totalTime = 0f;

    private var destinationAndVehicleInfo: HashMap<PlanetInfo, VehicleInfo> = hashMapOf()

    private fun getPlanetsAndVehicles() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingProgressLiveData.postValue(true)
            try {
                val planetsList = falconRepository.getPlanets()
                _loadingProgressLiveData.postValue(false)
                if (planetsList.isEmpty()) {
                    _travelSummary.postValue(getErrorMsg())
                } else {
                    _planetsList.postValue(planetsList)
                }
            } catch (ex: Exception) {
                _travelSummary.postValue(getErrorMsg())
            }

        }
        viewModelScope.launch(Dispatchers.IO) {
            _loadingProgressLiveData.postValue(true)
            try {
                val vehicleList = falconRepository.getVehicles()
                _loadingProgressLiveData.postValue(false)
                if (vehicleList.isEmpty()) {
                    _travelSummary.postValue(getErrorMsg())
                } else {
                    _vehiclesList.postValue(vehicleList)
                }
            } catch (ex: Exception) {
                _travelSummary.postValue(getErrorMsg())
            }

        }
    }

    fun fetchData() {
        getToken()
        getPlanetsAndVehicles()
    }

    private fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Anirudh", "getToken VM")
            try {
                val response = falconRepository.getToken()
                RetrofitInstance.ACCESS_TOKEN = response.token
            } catch (ex: Exception) {
                _travelSummary.postValue(getErrorMsg())
            }
        }
    }

    fun updateDestinationVehicleInfo(
        destination: ArrayList<InfoItem?>,
        vehicle: ArrayList<InfoItem?>
    ) {
        destinationAndVehicleInfo.clear()
        if (!areDestinationsAndPlanetsUnique(destination, vehicle)) {
            Log.d("Anirudh", "Destination and planets not unique return")
            return
        }
        for (i in 0..3) {
            destinationAndVehicleInfo[destination[i] as PlanetInfo] = vehicle[i] as VehicleInfo
        }
    }

    private fun areDestinationsAndPlanetsUnique(
        destinationsInfo: ArrayList<InfoItem?>,
        vehiclesInfo: ArrayList<InfoItem?>
    ): Boolean {
        val destinations = destinationsInfo.toMutableSet()
        val vehicles = vehiclesInfo.toMutableSet()
        return (destinations.size == 4 && vehicles.size == 4)
    }

    fun validateTravel(
        destinationsInfo: ArrayList<InfoItem?>,
        vehiclesInfo: ArrayList<InfoItem?>
    ): Boolean {

        var res = ""
        if (!areDestinationsAndPlanetsUnique(
                destinationsInfo,
                vehiclesInfo
            ) || destinationAndVehicleInfo.size != 4
        ) {
            res = getTravelNotPossibleSameVehicle()
            _travelSummary.value = res
            return false
        }
        val temp = destinationAndVehicleInfo
        var timeTaken = 0f
        for ((planet, vehicle) in temp) {
            if (planet.distance > vehicle.totalUnits * vehicle.maxDistance) {
                res += getTravelNotPossible(planet, vehicle) + "\n"
            } else {
                timeTaken += (planet.distance / vehicle.speed)
            }
        }
        if (res.isEmpty()) {
            totalTime = timeTaken
            return true
        }
        _travelSummary.value = res

        return false
    }


    private fun getTravelNotPossible(planetInfo: PlanetInfo, vehicle: VehicleInfo): String {
        return getApplication<Application>().resources.getString(
            R.string.travel_not_possible,
            planetInfo.name,
            vehicle.name
        )
    }

    private fun getTravelPossible(): String {
        return getApplication<Application>().resources.getString(R.string.travel_possible)
    }

    private fun getTravelNotPossibleSameVehicle(): String {
        return getApplication<Application>().resources.getString(R.string.travel_not_possible_sameVehicle)
    }

    private fun getSearchFalconeSuccess(): String {
        return getApplication<Application>().resources.getString(R.string.search_success)
    }

    private fun getSearchFalconeFailed(): String {
        return getApplication<Application>().resources.getString(R.string.search_fail)
    }

    private fun getErrorMsg(): String {
        return getApplication<Application>().resources.getString(R.string.error_msg)
    }


    fun findFalcone() {
        viewModelScope.launch(Dispatchers.IO) {
            val planetsList = destinationAndVehicleInfo.keys.map {
                it.name
            }.toList()
            val vehiclesList = destinationAndVehicleInfo.values.map {
                it.name
            }.toList()
            try {
                val result =
                    falconRepository.findFalcon(
                        RetrofitInstance.ACCESS_TOKEN,
                        planetsList as ArrayList<String>,
                        vehiclesList as ArrayList<String>
                    )
                if (result.status.equals("success")) {
                    _findSuccess.postValue(Pair(result.planetName ?: "", true))
                } else if (result.status.equals("false")) {
                    _findSuccess.postValue(Pair(getSearchFalconeFailed(), false))
                } else { //token error
                    getToken()
                    _travelSummary.postValue(getErrorMsg())
                }
            } catch (ex: Exception) {
                _travelSummary.postValue(getErrorMsg())
            }
        }
    }

    fun getTotalTime(): String {
        return totalTime.toString()
    }

    fun resetResult() {
        _travelSummary.postValue(null)
        _findSuccess.postValue(null)
    }

}
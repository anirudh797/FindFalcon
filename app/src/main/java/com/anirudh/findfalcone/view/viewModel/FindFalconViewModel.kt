package com.anirudh.findfalcone.view.viewModel

import android.app.Application
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
    private val application: Application
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

    private var _travelSummary = MutableLiveData<String>()
    val travelSummary: LiveData<String> = _travelSummary

    private var destinationAndVehicleInfo: HashMap<PlanetInfo, VehicleInfo> = hashMapOf()

    private fun getPlanetsAndVehicles() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingProgressLiveData.postValue(true)
            val planetsList = falconRepository.getPlanets()
            _loadingProgressLiveData.postValue(false)
            if (planetsList.isEmpty()) {
                _showError.postValue(true)
            } else {
                _planetsList.postValue(planetsList)
                _showError.postValue(false)
            }
        }
        viewModelScope.launch {
            _loadingProgressLiveData.postValue(true)
            val vehicleList = falconRepository.getVehicles()
            _loadingProgressLiveData.postValue(false)
            if (vehicleList.isEmpty()) {
                _showError.postValue(true)
            } else {
                _vehiclesList.postValue(vehicleList)
                _showError.postValue(false)
            }
        }
    }

    fun fetchData() {
        getToken()
        getPlanetsAndVehicles()
    }

    private fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
//            _loadingProgressLiveData.postValue(true)
            val response = falconRepository.getToken()
            RetrofitInstance.ACCESS_TOKEN = response.token
//            _loadingProgressLiveData.postValue(false)
//            _vehiclesList.postValue(vehicleList)
            _showError.postValue(false)
        }
    }

    fun updateDestinationVehicleInfo(
        destination: ArrayList<InfoItem?>,
        vehicle: ArrayList<InfoItem?>
    ) {
        destinationAndVehicleInfo.clear()
        if (!isDestinationsAndPlanetsUnique(destination, vehicle)) {
            return
        }
        for (i in 0..3) {
            destinationAndVehicleInfo[destination[i] as PlanetInfo] = vehicle[i] as VehicleInfo
        }
    }

    private fun isDestinationsAndPlanetsUnique(
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
        if (!isDestinationsAndPlanetsUnique(
                destinationsInfo,
                vehiclesInfo
            ) || destinationAndVehicleInfo.size != 4
        ) {
            res = getTravelNotPossibleSameVehicle()
            _travelSummary.value = res
            return false
        }
        val temp = destinationAndVehicleInfo
        for ((planet, vehicle) in temp) {
            if (planet.distance > vehicle.totalUnits * vehicle.maxDistance) {
                res += getTravelNotPossible(planet, vehicle) + "\n"
            }
        }
        if (res.isEmpty()) {
            res = getTravelPossible()
            _travelSummary.value = res
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

    fun findFalcone() {

    }

}
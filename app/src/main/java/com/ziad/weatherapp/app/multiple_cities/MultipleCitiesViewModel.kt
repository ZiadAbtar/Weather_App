package com.ziad.weatherapp.app.multiple_cities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziad.weatherapp.app.base.BaseViewModel
import com.ziad.weatherapp.data.remote.repository.MultipleCitiesRepository
import com.ziad.weatherapp.data.remote.request.CityWeatherRequest
import kotlinx.coroutines.launch

class MultipleCitiesViewModel : BaseViewModel() {

    var citiesMap = MutableLiveData<HashMap<String, Int>>()

    fun loadCitiesMap() {
        viewModelScope.launch {
            val result = MultipleCitiesRepository.loadCities()
            citiesMap.setValue(result)
        }
    }

    fun getCitiesWeather(cityWeatherRequest: CityWeatherRequest) {
        viewModelScope.launch {
            handleResult(
                MultipleCitiesRepository.getCurrentWeatherByCitiesIds(cityWeatherRequest)
            )
        }
    }


}
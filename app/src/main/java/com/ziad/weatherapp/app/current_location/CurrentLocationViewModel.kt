package com.ziad.weatherapp.app.current_location

import androidx.lifecycle.viewModelScope
import com.ziad.weatherapp.app.base.BaseViewModel
import com.ziad.weatherapp.data.remote.repository.CurrentCityRepository
import com.ziad.weatherapp.data.remote.request.CurrentCityRequest
import kotlinx.coroutines.launch

class CurrentLocationViewModel : BaseViewModel() {

    fun getCurrentCityWeather(currentCityRequest: CurrentCityRequest) {
        viewModelScope.launch {
            handleResult(
                CurrentCityRepository.getCurrentWeatherByCitiesIds(currentCityRequest)
            )
        }
    }
}
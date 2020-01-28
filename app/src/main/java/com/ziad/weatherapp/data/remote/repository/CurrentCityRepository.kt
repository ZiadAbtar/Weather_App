package com.ziad.weatherapp.data.remote.repository

import com.ziad.weatherapp.data.remote.APIServer
import com.ziad.weatherapp.data.remote.request.CurrentCityRequest


object CurrentCityRepository : BaseRepository() {
    suspend fun getCurrentWeatherByCitiesIds(currentCityRequest: CurrentCityRequest) =
        safeApiCall { APIServer.apiServer.getCurrentCityWeather(currentCityRequest) }
}
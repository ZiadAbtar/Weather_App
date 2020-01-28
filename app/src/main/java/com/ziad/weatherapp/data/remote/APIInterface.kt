package com.ziad.weatherapp.data.remote


import com.ziad.weatherapp.data.remote.request.CityWeatherRequest
import com.ziad.weatherapp.data.remote.response.CityWeatherResponse
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {
    @GET("group")
    suspend fun getCurrentWeatherForCities(@QueryMap cityWeatherRequest: CityWeatherRequest): Response<CityWeatherResponse>
}
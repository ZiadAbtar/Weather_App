package com.ziad.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ziad.weatherapp.data.remote.response.base.BaseResponse

data class CityWeatherResponse(
    val count: Int? = 0,
    val id: Int? = 0,
    val name: String? = "",
    @SerializedName("weather") val weatherList: List<Weather>? = null,
    val main: Main? = null,
    val wind: Wind? = null
) : BaseResponse()

data class Weather(
    val main: String? = "",
    val description: String? = ""
)

data class Main(
    val temp: Float? = 0f,
    val temp_min: Float? = 0f,
    val temp_max: Float? = 0f
)

data class Wind(
    val speed: Float? = 0f
)
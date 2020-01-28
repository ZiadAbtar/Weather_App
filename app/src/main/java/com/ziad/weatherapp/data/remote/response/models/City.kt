package com.ziad.weatherapp.data.remote.response.models

import com.google.gson.annotations.SerializedName

data class City(
    val id: Int? = 0,
    val name: String? = "",
    @SerializedName("weather") val weatherList: List<Weather>? = null,
    val main: Main? = null,
    val wind: Wind? = null
)

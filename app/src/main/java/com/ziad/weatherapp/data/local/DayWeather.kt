package com.ziad.weatherapp.data.local

import com.ziad.weatherapp.data.remote.response.models.Data

data class DayWeather(
    val day: String = "",
    val data: ArrayList<Data> = ArrayList()
)
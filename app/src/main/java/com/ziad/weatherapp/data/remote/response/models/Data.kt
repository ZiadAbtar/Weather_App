package com.ziad.weatherapp.data.remote.response.models

import com.google.gson.annotations.SerializedName

data class Data(
    val id: Int? = 0,
    @SerializedName("weather") val weatherList: List<Weather>? = null,
    val main: Main? = null,
    val wind: Wind? = null,
    val dt: Long = 0,
    val dt_text: Long? = 0,
    @Transient var myDate: String = "",
    @Transient var myHour: String = ""
)
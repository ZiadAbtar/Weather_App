package com.ziad.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ziad.weatherapp.data.remote.response.base.BaseResponse
import com.ziad.weatherapp.data.remote.response.models.City

data class CityWeatherResponse(
    @SerializedName("cnt") val count: Int? = 0,
    @SerializedName("list") val cities: ArrayList<City>? = null
) : BaseResponse()
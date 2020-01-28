package com.ziad.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.ziad.weatherapp.data.remote.response.base.BaseResponse
import com.ziad.weatherapp.data.remote.response.models.City
import com.ziad.weatherapp.data.remote.response.models.Data

data class CurrentCityResponse(
    @SerializedName("cnt") val count: Int? = 0,
    @SerializedName("list") val data: List<Data>? = null,
    val city: City
) : BaseResponse()
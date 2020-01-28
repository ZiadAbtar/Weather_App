package com.ziad.weatherapp.data.remote.request

class CurrentCityRequest : BaseRequest() {

    fun setLat(lat: Double): CurrentCityRequest {
        put("lat", lat.toString())
        return this
    }

    fun setLon(lon: Double): CurrentCityRequest {
        put("lon", lon.toString())
        return this
    }
}
package com.ziad.weatherapp.data.remote.request

class CityWeatherRequest : BaseRequest() {

    fun setCityIds(vararg ids: Int): CityWeatherRequest {
        var stringIds = ""

        for (i in ids.indices) {
            stringIds = stringIds.plus(ids[i])
            if (i != ids.size - 1) {
                stringIds = stringIds.plus(",")
            }
        }
        put("id", stringIds)

        return this
    }
}
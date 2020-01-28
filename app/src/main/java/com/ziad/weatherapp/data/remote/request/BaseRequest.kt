package com.ziad.weatherapp.data.remote.request

open class BaseRequest : HashMap<String, String>() {

    init {
        put("appid", "2ddb73768cf69b6f867f28cc0c9ba4c8")
        put("units", "metric")
    }
}
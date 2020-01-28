package com.ziad.weatherapp.data.remote.exception

import com.ziad.weatherapp.data.remote.response.base.ApiError

class APIException : RuntimeException {
    var apiError: ApiError? = null
        private set

    constructor(message: String = "") : super(message)
    constructor(apiError: ApiError?) : super(apiError?.message) {
        this.apiError = apiError
    }

    override fun toString(): String {
        return "APIException{" +
                "apiError=" + apiError.toString() +
                '}'
    }
}
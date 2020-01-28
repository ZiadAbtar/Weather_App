package com.ziad.weatherapp.data.remote.response.base

import com.ziad.weatherapp.data.remote.exception.APIException


sealed class ResultWrapper<out T> {
    data class Success<out T : BaseResponse>(val value: T) : ResultWrapper<T>()
    data class APIError(val exception: APIException) : ResultWrapper<Nothing>()
    data class UnhandledException(val throwable: Throwable) : ResultWrapper<Nothing>()
}
package com.ziad.weatherapp.data.remote.response.base

import com.ziad.weatherapp.data.remote.exception.APIException


interface OnResultReceived<R : BaseResponse> {
    fun onUnhandledException(throwable: Throwable, callable: () -> Unit)
    fun onAPIException(apiException: APIException, callable: () -> Unit)
    fun onSuccess(response: R)
}
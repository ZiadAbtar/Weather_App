package com.ziad.weatherapp.data.remote.repository

import com.ziad.weatherapp.data.remote.exception.APIException
import com.ziad.weatherapp.data.remote.response.base.BaseResponse
import com.ziad.weatherapp.data.remote.response.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseRepository {
    suspend fun <RESPONSE : BaseResponse, T : Response<RESPONSE>> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): ResultWrapper<RESPONSE> {
        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke() as Response<*>
                if (response.body() is BaseResponse) {
                    val baseResponse = response.body() as RESPONSE
                    if (baseResponse.apiError != null) {
                        throw APIException(apiError = baseResponse.apiError)
                    }
                    ResultWrapper.Success(baseResponse)
                } else {
                    throw RuntimeException("response is not BaseResponse")
                }
            } catch (throwable: Throwable) {
                if (throwable is APIException) {
                    ResultWrapper.APIError(throwable)
                } else {
                    ResultWrapper.UnhandledException(throwable)
                }
            }
        }
    }
}
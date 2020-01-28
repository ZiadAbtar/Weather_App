package com.ziad.weatherapp.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ziad.weatherapp.data.remote.response.base.BaseResponse
import com.ziad.weatherapp.data.remote.response.base.ResultWrapper
import kotlinx.coroutines.cancel

open class BaseViewModel : ViewModel() {
    fun cancelRequests() = viewModelScope.coroutineContext.cancel()

    val liveData = MutableLiveData<ResultWrapper<*>>()

    fun <RESPONSE : BaseResponse> handleResult(
        result: ResultWrapper<RESPONSE>
    ) {
        liveData.postValue(result)
    }
}
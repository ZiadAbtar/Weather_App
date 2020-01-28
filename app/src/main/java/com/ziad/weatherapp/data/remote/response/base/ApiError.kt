package com.ziad.weatherapp.data.remote.response.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiError(
    val cod: Int? = 0,
    val message: String? = ""
) : Parcelable
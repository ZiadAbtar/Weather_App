package com.ziad.weatherapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object APIServer {

    @JvmStatic
    val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }

    private var retrofit = buildRetrofit()

    private var _apiServer = retrofit.create(APIInterface::class.java)

    @JvmStatic
    val apiServer: APIInterface
        get() = _apiServer

}

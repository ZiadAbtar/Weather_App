package com.ziad.weatherapp.data.remote.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.ziad.weatherapp.WeahterApplication
import com.ziad.weatherapp.data.local.City
import com.ziad.weatherapp.data.remote.APIServer
import com.ziad.weatherapp.data.remote.request.CityWeatherRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader


object MultipleCitiesRepository : BaseRepository() {


    suspend fun getCurrentWeatherByCitiesIds(cityWeatherRequest: CityWeatherRequest) =
        safeApiCall { APIServer.apiServer.getCurrentWeatherForCities(cityWeatherRequest) }


    suspend fun loadCities(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): HashMap<String, Int> {
        return withContext(dispatcher) {
            val time = System.currentTimeMillis()
            Log.d("TIMING", "TIMING STARTED")
            val gson = GsonBuilder().create()
            val entries = HashMap<String, Int>()
            val inputStream: InputStream =
                WeahterApplication.instance.get().assets.open("city.list.json")
            val reader = JsonReader(InputStreamReader(inputStream, "UTF-8"))

            reader.beginArray()

            while (reader.hasNext()) {
                val city: City = gson.fromJson(reader, City::class.java)
                if (!city.name.isNullOrBlank() && city.id != null) {
                    entries[city.name] = city.id
                }
            }
            reader.endArray()
            reader.close()
            Log.d("TIMING", "TIMING ENDED --- time spent= ${System.currentTimeMillis() - time}")
            entries
        }
    }
}
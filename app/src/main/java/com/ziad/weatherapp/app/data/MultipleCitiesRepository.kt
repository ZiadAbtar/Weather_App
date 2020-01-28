package com.ziad.weatherapp.app.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.ziad.weatherapp.WeahterApplication
import com.ziad.weatherapp.app.data.local.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader


object MultipleCitiesRepository {

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
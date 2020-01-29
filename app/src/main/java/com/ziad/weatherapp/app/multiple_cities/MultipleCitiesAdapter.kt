package com.ziad.weatherapp.app.multiple_cities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ziad.weatherapp.R
import com.ziad.weatherapp.data.remote.response.models.City

class MultipleCitiesAdapter(val context: Context) :
    RecyclerView.Adapter<MultipleCitiesAdapter.CityWeatherViewHolder>() {

    private var cities: List<City> = emptyList()

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityWeatherViewHolder {
        return CityWeatherViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_weather,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityWeatherViewHolder, position: Int) {
        val city = cities[position]
        with(holder) {
            maxTempTextView.text = city.main?.temp_max.toString() + " °C"
            minTempTextView.text = city.main?.temp_min.toString() + " °C"
            cityNameTextView.text = city.name
            val desc = city.weatherList?.get(0)
            desc?.let {
                weatherDescTextView.text = it.description + " / " + it.main
            }
            windTextView.text = city.wind?.speed.toString() + " Km/h"
        }
    }

    class CityWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val maxTempTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_temp_max) }
        val minTempTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_temp_min) }
        val cityNameTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_city_name) }
        val weatherDescTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_weather_description) }
        val windTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_wind_speed) }
    }
}
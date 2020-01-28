package com.ziad.weatherapp.app.current_location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ziad.weatherapp.R
import com.ziad.weatherapp.data.remote.response.models.Data

class CurrentCityAdapter(val context: Context) :
    RecyclerView.Adapter<CurrentCityAdapter.CurrentCityWeather>() {

    private var days: List<Data> = emptyList()

    fun setData(days: List<Data>) {
        this.days = days
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentCityWeather {
        return CurrentCityWeather(
            LayoutInflater.from(context).inflate(
                R.layout.item_weather,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: CurrentCityWeather, position: Int) {
        val day = days[position]
        with(holder) {
            cityNameTextView.text = "${day.myHour}h"
            dayTextView.text = day.myDate
            if (position == 0) {
                dayTextView.visibility = View.VISIBLE
            } else {
                val previousItem = days[position - 1]
                if (day.myDate == previousItem.myDate) {
                    dayTextView.visibility = View.GONE
                } else {
                    dayTextView.visibility = View.VISIBLE
                }
            }
            maxTempTextView.text = day.main?.temp_max.toString()
            minTempTextView.text = day.main?.temp_min.toString()
            val desc = day.weatherList?.get(0)
            desc?.let {
                weatherDescTextView.text = it.description + " / " + it.main
            }
            windTextView.text = day.wind?.speed.toString()
        }
    }

    class CurrentCityWeather(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val maxTempTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_temp_max) }
        val minTempTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_temp_min) }
        val cityNameTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_city_name) }
        val weatherDescTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_weather_description) }
        val windTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_wind_speed) }
        val dayTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_day) }
    }
}
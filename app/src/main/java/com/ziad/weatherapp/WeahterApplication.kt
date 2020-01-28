package com.ziad.weatherapp

import android.app.Application
import java.lang.RuntimeException

class WeahterApplication : Application() {

    companion object {
        lateinit var instance: WeahterApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun get(): WeahterApplication {
        if (instance == null) {
            throw RuntimeException("WeatherApplication instance not set")
        }
        return instance
    }
}
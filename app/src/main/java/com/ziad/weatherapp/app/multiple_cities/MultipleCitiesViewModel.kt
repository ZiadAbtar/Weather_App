package com.ziad.weatherapp.app.multiple_cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MultipleCitiesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is cities Fragment"
    }
    val text: LiveData<String> = _text
}
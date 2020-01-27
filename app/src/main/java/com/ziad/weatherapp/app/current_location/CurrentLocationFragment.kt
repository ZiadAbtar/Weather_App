package com.ziad.weatherapp.app.current_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ziad.weatherapp.R

class CurrentLocationFragment : Fragment() {

    private lateinit var currentLocationViewModel: CurrentLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentLocationViewModel =
            ViewModelProvider.NewInstanceFactory().create(CurrentLocationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_current_location, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        currentLocationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
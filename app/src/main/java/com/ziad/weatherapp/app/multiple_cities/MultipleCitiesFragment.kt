package com.ziad.weatherapp.app.multiple_cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ziad.weatherapp.R

class MultipleCitiesFragment : Fragment() {

    private lateinit var multipleCitiesViewModel: MultipleCitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        multipleCitiesViewModel =
            ViewModelProvider.NewInstanceFactory().create(MultipleCitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_multiple_cities, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        multipleCitiesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
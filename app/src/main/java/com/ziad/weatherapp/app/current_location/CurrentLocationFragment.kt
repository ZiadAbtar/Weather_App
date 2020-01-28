package com.ziad.weatherapp.app.current_location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ziad.weatherapp.R

class CurrentLocationFragment : Fragment() {

    private val MY_PERMISSION_ACCESS_COURSE_LOCATION: Int = 123

    private lateinit var currentLocationViewModel: CurrentLocationViewModel
    private var locationManager: LocationManager? = null
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
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager

        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSION_ACCESS_COURSE_LOCATION
            )
        } else {
            startLocationTracker()
        }

        return root
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Toast.makeText(
                requireContext(),
                "" + location.longitude + ":" + location.latitude,
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == MY_PERMISSION_ACCESS_COURSE_LOCATION
        ) {

            startLocationTracker()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationTracker() {
        val isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        val provider =
            if (isGPSEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER

        val lastKnownLocation = locationManager?.getLastKnownLocation(provider)

        locationManager?.requestLocationUpdates(
            provider,
            30000L,
            1000f,
            locationListener
        )
    }

    override fun onPause() {
        super.onPause()

    }
}
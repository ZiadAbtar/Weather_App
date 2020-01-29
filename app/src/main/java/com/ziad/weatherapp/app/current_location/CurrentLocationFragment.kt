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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ziad.weatherapp.R
import com.ziad.weatherapp.app.base.BaseFragment
import com.ziad.weatherapp.data.remote.request.CurrentCityRequest
import com.ziad.weatherapp.data.remote.response.CurrentCityResponse
import com.ziad.weatherapp.data.remote.response.models.Data
import java.util.*

class CurrentLocationFragment : BaseFragment<CurrentCityResponse, CurrentLocationViewModel>() {

    private val MY_PERMISSION_ACCESS_COURSE_LOCATION: Int = 123

    private var locationManager: LocationManager? = null

    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private lateinit var mCityNameTextView: TextView
    private lateinit var mRecyclerView: RecyclerView


    override val getViewModel: Class<CurrentLocationViewModel>
        get() = CurrentLocationViewModel::class.java
    override val getLayoutId: Int
        get() = R.layout.fragment_current_location

    override fun callPrimaryApi() {
        showProgress()
        viewModel.getCurrentCityWeather(CurrentCityRequest().setLat(lat).setLon(lon))
    }

    override fun onSuccess(response: CurrentCityResponse) {
        if (response.data.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.Unexpected_error, Toast.LENGTH_LONG).show()
            return
        }
        val adapter = CurrentCityAdapter(requireContext())
        mCityNameTextView.text = response.city.name
        mRecyclerView.adapter = adapter
        adapter.setData(response.data)
        clubByDay(response.data)

    }

    private fun clubByDay(data: List<Data>) {

        val calendar = Calendar.getInstance()
        data.forEach {
            calendar.timeInMillis = it.dt * 1000
            val calDay = calendar.get(Calendar.DAY_OF_MONTH)
            val calMonth = calendar.get(Calendar.MONTH)
            val calYear = calendar.get(Calendar.YEAR)
            val stringDate = "$calDay/${calMonth + 1}/$calYear"

            it.myDate = stringDate

            it.myHour = calendar.get(Calendar.HOUR_OF_DAY).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mCityNameTextView = view.findViewById(R.id.tv_city_name)
        mRecyclerView = view.findViewById(R.id.rv_weather)
        if (mRecyclerView.itemDecorationCount == 0) {
            mRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationManager = mActivity.getSystemService(LOCATION_SERVICE) as LocationManager

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
                mActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                MY_PERMISSION_ACCESS_COURSE_LOCATION
            )
        } else {
            startLocationTracker()
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            lat = location.latitude
            lon = location.longitude
            callPrimaryApi()
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
            mActivity.onBackPressed()
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationTracker() {
        val isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        val provider =
            if (isGPSEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER

        val lastKnownLocation = locationManager?.getLastKnownLocation(provider)
        lat = lastKnownLocation?.latitude ?: 0.0
        lon = lastKnownLocation?.longitude ?: 0.0
        callPrimaryApi()
        locationManager?.requestLocationUpdates(
            provider,
            30000L,
            1000f,
            locationListener
        )
    }

    override fun onPause() {
        super.onPause()
        locationManager?.removeUpdates(locationListener)
    }
}
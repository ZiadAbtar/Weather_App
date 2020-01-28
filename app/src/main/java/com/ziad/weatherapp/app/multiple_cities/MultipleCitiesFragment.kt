package com.ziad.weatherapp.app.multiple_cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ziad.weatherapp.R
import com.ziad.weatherapp.app.base.BaseFragment
import com.ziad.weatherapp.data.remote.request.CityWeatherRequest
import com.ziad.weatherapp.data.remote.response.CityWeatherResponse
import java.util.*
import kotlin.collections.ArrayList


class MultipleCitiesFragment : BaseFragment<CityWeatherResponse, MultipleCitiesViewModel>(),
    View.OnClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCitiesEditText: EditText
    private lateinit var mProceedButton: Button

    private var enteredCitiesIds = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.rv_cities_weather)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        mCitiesEditText = view.findViewById(R.id.et_cities)
        mProceedButton = view.findViewById(R.id.btn_fetch)
        mProceedButton.setOnClickListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress()
        viewModel.loadCitiesMap()
        viewModel.citiesMap.observe(viewLifecycleOwner, Observer {
            hideProgress()
            val result = it
        })
    }

    override val getViewModel: Class<MultipleCitiesViewModel>
        get() = MultipleCitiesViewModel::class.java
    override val getLayoutId: Int
        get() = R.layout.fragment_multiple_cities

    override fun callPrimaryApi() {
        showProgress()
        val cityWeatherRequest = CityWeatherRequest().setCityIds(enteredCitiesIds)
        viewModel.getCitiesWeather(cityWeatherRequest)
    }

    override fun onSuccess(response: CityWeatherResponse) {
        if (response.cities.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.Unexpected_error, Toast.LENGTH_LONG).show()
            return
        }
        val adapter = MultipleCitiesAdapter(requireContext())
        mRecyclerView.adapter = adapter
        adapter.setCities(response.cities)
    }

    override fun showProgress() {
        super.showProgress()
        mProceedButton.visibility = View.GONE
        mRecyclerView.visibility = View.GONE
    }

    override fun hideProgress() {
        super.hideProgress()
        mProceedButton.visibility = View.VISIBLE
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_fetch) {
            validateCitiesAndProceed()
        }
    }

    private fun validateCitiesAndProceed() {
        enteredCitiesIds.clear()
        val text = mCitiesEditText.text.toString().trim()
        val cities = text.split(",").map { it.trim() }
        if (cities.isNullOrEmpty() || cities.size < 3 || cities.size > 7) {
            mCitiesEditText.error = getString(R.string.check_conditions)
            return
        } else {
            var notFoundCities = ""
            cities.forEach {
                val value = viewModel.citiesMap.value?.get(it.toLowerCase(Locale.getDefault()))
                if (value == null) {
                    notFoundCities = notFoundCities.plus(it).plus(" - ")
                } else {
                    enteredCitiesIds.add(value)
                }
            }
            if (notFoundCities.isNotBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.cities_not_found, notFoundCities),
                    Toast.LENGTH_LONG
                ).show()
                return
            }
        }


        callPrimaryApi()
    }

}
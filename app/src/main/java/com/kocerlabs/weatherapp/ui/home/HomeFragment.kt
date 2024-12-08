package com.kocerlabs.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kocerlabs.weatherapp.KeyObject
import com.kocerlabs.weatherapp.R
import com.kocerlabs.weatherapp.databinding.FragmentHomeBinding
import com.kocerlabs.weatherapp.ui.base.BaseFragment
import com.kocerlabs.weatherapp.ui.recylerview.ForecastAdapter
import com.kocerlabs.weatherapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val TAG = "HomeFragment"
    private val viewModel: HomeViewModel by viewModels()
    private val forecastAdapter by lazy { ForecastAdapter() }
    private val calendar by lazy { Calendar.getInstance() }


    override fun getViewBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            var lat = 40.15531200
            var lon = 26.41416000
            var name = "Canakkale"
            cityTxt.text = name
            progressBar.visible(true)
            detailLayout.visibility = View.INVISIBLE
            viewModel.getCurrentWeather(lat, lon, "metric", KeyObject.KEY)
            // forecast temp
            viewModel.getForecastWeather(lat, lon, KeyObject.KEY)

            addCity.setOnClickListener(::goToCityFragment)
        }


        val args = HomeFragmentArgs.fromBundle(requireArguments())

        if (args.lon != -10000f && args.lat != -10000f && args.name != "Unknown") {
            val lon = args.lon.toDouble()
            val lat = args.lat.toDouble()
            viewModel.getCurrentWeather(lat, lon, "metric", KeyObject.KEY)
            viewModel.getForecastWeather(lat, lon, KeyObject.KEY)
        }


        observers()

        // settings Blue View
        val radius = 10f
        val decorView = requireActivity().window.decorView
        val rootView: ViewGroup? = (decorView.findViewById(android.R.id.content) as ViewGroup?)
        val windowBackground = decorView.background

        rootView?.let {
            with(binding) {
                blueView.setupWith(it, RenderScriptBlur(requireContext()))
                    .setFrameClearDrawable(windowBackground)
                    .setBlurRadius(radius)

                blueView.outlineProvider = ViewOutlineProvider.BACKGROUND
                blueView.clipToOutline = true
            }
        }


    }


    private fun observers() {
        observeCurrentWeather()
        observeForecastWeather()

    }


    private fun observeCurrentWeather() {
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            with(binding) {
                progressBar.visible(false)
                detailLayout.visibility = View.VISIBLE

                it?.let {
                    statusTxt.text = it.weather?.get(0)?.main ?: "-"
                    windTxt.text =
                        it.wind?.speed?.let { speed -> Math.round(speed).toString() } + "KM"
                    currentTempTxt.text =
                        it.main?.temp?.let { temp -> Math.round(temp).toString() } + "°"
                    maxTempTxt.text =
                        it.main?.tempMax?.let { temp -> Math.round(temp).toString() } + "°"
                    minTempTxt.text =
                        it.main?.tempMin?.let { temp -> Math.round(temp).toString() } + "°"

                    humidityTxt.text = it.main?.humidity?.toString() + "%"

                    val drawable = if (isNightNow()) R.drawable.night_bg
                    else {
                        it.weather?.get(0)?.icon.let { icon -> setDynamicallyWallpaper(icon) }
                    }
                    bgImage.setImageResource(drawable)
                }
            }

        })
    }


    private fun observeForecastWeather() {
        viewModel.forecast.observe(viewLifecycleOwner, Observer {
            with(binding) {
                progressBar.visible(false)
                blueView.visibility = View.VISIBLE

                forecastAdapter.differ.submitList(it.list)
                Log.d(TAG, "Adapter list updated: ${forecastAdapter.differ.currentList}")

                forecastView.apply {
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    adapter = forecastAdapter
                }
                Log.d(TAG, "Forecast weather response: $it")
            }

        })
    }


    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }


    private fun setDynamicallyWallpaper(icon: String?): Int {
        return when (icon?.dropLast(1)) {
            "01" -> R.drawable.sunny_bg
            "02", "03", "04" -> R.drawable.cloudy_bg
            "09", "10", "11" -> R.drawable.rainy_bg
            "13" -> R.drawable.snow_bg
            "50" -> R.drawable.haze_bg
            else -> R.drawable.sunny_bg
        }
    }


    private fun goToCityFragment(view: View) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCityListFragment(
            )
        )
    }

}
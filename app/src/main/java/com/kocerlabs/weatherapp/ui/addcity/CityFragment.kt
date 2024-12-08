package com.kocerlabs.weatherapp.ui.addcity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kocerlabs.weatherapp.KeyObject
import com.kocerlabs.weatherapp.databinding.FragmentCityBinding
import com.kocerlabs.weatherapp.ui.base.BaseFragment
import com.kocerlabs.weatherapp.ui.recylerview.CityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : BaseFragment<FragmentCityBinding>() {
    private val viewModel: AddCityViewModel by viewModels()
    private val TAG = "CityListFragment"

    override fun getViewBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCityBinding = FragmentCityBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDirectGeo("Çanakkale", 10, KeyObject.KEY)
        observers()

        with(binding) {
            cityEdt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    progressBar2.visibility = View.VISIBLE
                    viewModel.getDirectGeo(s.toString(), 10, KeyObject.KEY)
                }

            })
        }

    }


    private fun observers() {
        observeGeoLocation()
    }


    private fun observeGeoLocation() {
        viewModel.geo.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "İT: $it")
            with(binding) {
                progressBar2.visibility = View.GONE

                val cityAdapter = CityAdapter({ name, lat, lon ->
                    // NavController ile action başlatın
                    val action = CityFragmentDirections.actionCityListFragmentToHomeFragment(
                        name = name,
                        lat = lat,
                        lon = lon
                    )
                    findNavController().navigate(action)
                })
                cityAdapter.differ.submitList(it)
                cityView.apply {
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    adapter = cityAdapter
                }
            }

        })
    }


}
package com.kocerlabs.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kocerlabs.weatherapp.databinding.FragmentHomeBinding
import com.kocerlabs.weatherapp.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val TAG = "HomeFragment"
    override fun getViewBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView.setOnClickListener {
            Log.d(TAG, "TİKLANDI")
        }
    }

}
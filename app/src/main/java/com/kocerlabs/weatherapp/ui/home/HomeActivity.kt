package com.kocerlabs.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kocerlabs.weatherapp.R
import com.kocerlabs.weatherapp.databinding.ActivityHomeBinding
import com.kocerlabs.weatherapp.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val TAG = "HomeActivity"


    override fun getViewBindingActivity(layoutInflater: LayoutInflater): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootHomeActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.fragmentContainerView.setOnClickListener {
            Log.d(TAG,"hello")
        }
    }


}
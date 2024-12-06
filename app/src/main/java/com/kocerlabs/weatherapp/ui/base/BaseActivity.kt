package com.kocerlabs.weatherapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

     lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBindingActivity(this.layoutInflater)
        setContentView(binding.root)

    }

    abstract fun getViewBindingActivity(layoutInflater: LayoutInflater): VB
}

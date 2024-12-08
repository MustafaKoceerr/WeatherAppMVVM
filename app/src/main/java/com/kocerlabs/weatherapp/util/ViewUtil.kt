package com.kocerlabs.weatherapp.util

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
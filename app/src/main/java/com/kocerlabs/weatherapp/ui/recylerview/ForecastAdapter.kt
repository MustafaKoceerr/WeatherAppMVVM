package com.kocerlabs.weatherapp.ui.recylerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kocerlabs.weatherapp.data.network.model.WeatherForecastItem
import com.kocerlabs.weatherapp.databinding.ForecastViewholderBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(val recyclerviewWeatherBinding: ForecastViewholderBinding) :
        ViewHolder(recyclerviewWeatherBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder =
        ForecastViewHolder(
            ForecastViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val binding = holder.recyclerviewWeatherBinding
        val date =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(differ.currentList[position].dtTxt.toString())
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dayOfWeekName = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> "Sun"
            2 -> "Mon"
            3 -> "Tue"
            4 -> "Wed"
            5 -> "Thu"
            6 -> "Fri"
            7 -> "Sat"
            else -> "-"
        }

        with(binding) {
            nameDayTxt.text = dayOfWeekName
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val amPm = if (hour < 12) "am" else "pm"
            val hour12 = calendar.get(Calendar.HOUR)
            hourTxt.text = hour12.toString() + amPm
            tempTxt.text =
                differ.currentList[position].main?.temp?.let { temp -> Math.round(temp) }
                    .toString() + "°"
        }

        val icon = when (differ.currentList[position].weather?.get(0)?.icon.toString()) {
            "01d", "01n" -> "sunny"
            "02d", "02n" -> "cloudy_sunny"
            "03d", "03n" -> "cloudy_sunny"
            "04d", "04n" -> "cloudy"
            "09d", "09n" -> "rainy"
            "10d", "10n" -> "rainy"
            "11d", "11n" -> "storm"
            "13d", "13n" -> "snowy"
            "50d", "50n" -> "windy"
            else -> "sunny"
        }

        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            icon,
            "drawable", binding.root.context.packageName
        )

        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.pic)
    }

    /**
     * Differ callback nasıl yapılır araştır ve uygula
     */

    private val differCallBack = object : DiffUtil.ItemCallback<WeatherForecastItem.WeatherData>() {

        override fun areItemsTheSame(
            oldItem: WeatherForecastItem.WeatherData,
            newItem: WeatherForecastItem.WeatherData
        ): Boolean {
            return oldItem == newItem // Benzersizlik kriteri
        }

        override fun areContentsTheSame(
            oldItem: WeatherForecastItem.WeatherData,
            newItem: WeatherForecastItem.WeatherData
        ): Boolean {
            return oldItem == newItem // İçerik aynı mı?
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}
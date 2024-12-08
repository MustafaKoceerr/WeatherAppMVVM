package com.kocerlabs.weatherapp.ui.recylerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kocerlabs.weatherapp.data.network.model.CityItem
import com.kocerlabs.weatherapp.databinding.CityViewholderBinding

class CityAdapter(
    private val onItemClick: (String, Float, Float) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(val recyclerviewCityBinding: CityViewholderBinding) :
        RecyclerView.ViewHolder(recyclerviewCityBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(
            CityViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val binding = holder.recyclerviewCityBinding
        val cityItem = differ.currentList[position]

        with(binding) {
            rowCityTxt.text = differ.currentList[position]!!.name
//                rowCityTxt.text = differ.currentList[position]!!.name
            rowCityTxt.setOnClickListener {
                onItemClick(
                    cityItem.name!!,
                    cityItem.lat!!.toFloat(),
                    cityItem.lon!!.toFloat(),
                )
            }
        }
    }

    val differCallBack = object : DiffUtil.ItemCallback<CityItem>() {

        override fun areItemsTheSame(
            oldItem: CityItem,
            newItem: CityItem
        ): Boolean {
            return oldItem == newItem // Benzersizlik kriteri

        }

        override fun areContentsTheSame(
            oldItem: CityItem,
            newItem: CityItem
        ): Boolean {
            return oldItem == newItem // İçerik aynı mı?
        }
    }


    val differ = AsyncListDiffer(this, differCallBack)

}
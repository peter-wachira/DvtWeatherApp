package com.zalocoders.dvtweatherapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zalocoders.dvtweatherapp.R
import com.zalocoders.dvtweatherapp.data.ForeCast
import com.zalocoders.dvtweatherapp.databinding.ForecastItemLayoutBinding
import com.zalocoders.dvtweatherapp.db.mappers.formatTime

class WeatherForeCastAdapter : ListAdapter<ForeCast, WeatherForeCastAdapter.ForeCastViewHolder>(diffUtil) {
    
    inner class ForeCastViewHolder(private val binding: ForecastItemLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: ForeCast) {
            with(binding) {
                tvDateName.text = formatTime(item.lastUpdated)
                tvWeatherValue.text = "${item.normalTemp} ℃\""
                
                val id = item.weatherCondition
                updateWeatherIcons(id)
            }
        }
        
        private fun updateWeatherIcons(id: String) {
            when {
                //Thunderstorm -2
                id.startsWith("2", true) -> {
                    updateHomeBackgrounds(R.drawable.rain)
                }
                
                //drizzle - 3
                id.startsWith("3", true) -> {
                    updateHomeBackgrounds(R.drawable.rain)
                }
                
                // Rain - 5
                id.startsWith("5", true) -> {
                    updateHomeBackgrounds(R.drawable.rain)
                }
                
                //Snow - 6
                id.startsWith("6", true) -> {
                    updateHomeBackgrounds(R.drawable.rain)
                    
                }
                
                //Atmosphere - 7
                id.startsWith("7", true) -> {
                    updateHomeBackgrounds(R.drawable.partlysunny)
                }
                
                // cloudy
                id.toInt() > 800 -> {
                    updateHomeBackgrounds(R.drawable.clouds)
                }
                
                id.equals("800", true) -> {
                    updateHomeBackgrounds(R.drawable.sun)
                }
                
                //sunny/clear - 8
                id.equals("8", true) -> {
                    updateHomeBackgrounds(R.drawable.clear)
                }
                
            }
        }
        
        private fun updateHomeBackgrounds(cloudyColor: Int) {
            binding.imgWeatherType.setImageResource(cloudyColor)
        }
        
    }
    
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): WeatherForeCastAdapter.ForeCastViewHolder {
        return ForeCastViewHolder(
                ForecastItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    
    override fun onBindViewHolder(holder: WeatherForeCastAdapter.ForeCastViewHolder, position: Int) {
        val item = getItem(position)
        
        holder.bind(item)
    }
}

val diffUtil = object : DiffUtil.ItemCallback<ForeCast>() {
    override fun areItemsTheSame(oldItem: ForeCast, newItem: ForeCast): Boolean {
        return oldItem.day == newItem.day
    }
    
    override fun areContentsTheSame(oldItem: ForeCast, newItem: ForeCast): Boolean {
        return oldItem == newItem
    }
    
    
}
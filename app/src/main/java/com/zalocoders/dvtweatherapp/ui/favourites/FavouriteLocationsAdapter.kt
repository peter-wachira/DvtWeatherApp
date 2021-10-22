package com.zalocoders.dvtweatherapp.ui.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zalocoders.dvtweatherapp.data.Favourite
import com.zalocoders.dvtweatherapp.databinding.FavouriteItemLayoutBinding


class FavouriteLocationsAdapter :
		ListAdapter<Favourite, FavouriteLocationsAdapter.LocationViewHolder>(diffUtil) {
	inner class LocationViewHolder(private val binding: FavouriteItemLayoutBinding) :
			RecyclerView.ViewHolder(binding.root) {
		
		@SuppressLint("SetTextI18n")
		fun bind(item: Favourite) {
			with(binding) {
				tvCityName.text = item.name
				tvTemp.text = "${item.normalTemp} ℃"
				tvCondition.text = item.weatherConditionName
			}
		}
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
			LocationViewHolder(
					FavouriteItemLayoutBinding.inflate(
							LayoutInflater.from(parent.context), parent, false
					)
			)
	
	override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
		holder.bind(getItem(position))
	}
	
}

val diffUtil = object : DiffUtil.ItemCallback<Favourite>() {
	override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
		return oldItem.lat == newItem.lat
	}
	
	override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
		return oldItem == newItem
	}
	
	
}
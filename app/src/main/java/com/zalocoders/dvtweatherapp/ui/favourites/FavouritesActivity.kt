package com.zalocoders.dvtweatherapp.ui.favourites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zalocoders.dvtweatherapp.databinding.ActivityFavouritesBinding

class FavouritesActivity : AppCompatActivity() {
	
	private val binding:ActivityFavouritesBinding by lazy{
		ActivityFavouritesBinding.inflate(layoutInflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
	}
	
	
}
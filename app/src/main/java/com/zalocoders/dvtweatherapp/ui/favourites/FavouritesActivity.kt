package com.zalocoders.dvtweatherapp.ui.favourites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.zalocoders.dvtweatherapp.R
import com.zalocoders.dvtweatherapp.databinding.ActivityFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesActivity : AppCompatActivity() {
	
	private val binding:ActivityFavouritesBinding by lazy{
		ActivityFavouritesBinding.inflate(layoutInflater)
	}
	
	private lateinit var appBarConfiguration:AppBarConfiguration
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		
		val navHostFragment =
				supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		 appBarConfiguration = AppBarConfiguration(
				topLevelDestinationIds = setOf(),
				fallbackOnNavigateUpListener = ::onSupportNavigateUp
		)
		
		setupActionBarWithNavController(navController, appBarConfiguration)
	}
	
	
	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment)
		return navController.navigateUp(appBarConfiguration)
				|| super.onSupportNavigateUp()
	}
	
}
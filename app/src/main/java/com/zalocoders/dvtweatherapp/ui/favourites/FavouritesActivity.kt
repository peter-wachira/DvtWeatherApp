package com.zalocoders.dvtweatherapp.ui.favourites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
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
	private lateinit var navController:NavController
	private lateinit var navHostFragment: NavHostFragment
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		
		 navHostFragment =
				supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		 navController = navHostFragment.navController
		
		 appBarConfiguration = AppBarConfiguration(
				topLevelDestinationIds = setOf(),
				fallbackOnNavigateUpListener = ::onSupportNavigateUp
		)
		
		setupActionBarWithNavController(navController, appBarConfiguration)
	}
	
	
	override fun onSupportNavigateUp(): Boolean {
		val navController = this.findNavController(R.id.nav_host_fragment)
		if(!navController.navigateUp()){
			onBackPressed()
		}
		return navController.navigateUp()
	}
}
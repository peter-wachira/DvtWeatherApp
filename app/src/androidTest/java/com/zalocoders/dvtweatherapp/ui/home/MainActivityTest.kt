package com.zalocoders.dvtweatherapp.ui.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.zalocoders.dvtweatherapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
	
	@ExperimentalCoroutinesApi
	@get: Rule
	val activityRule = ActivityScenarioRule(MainActivity::class.java)
	
	@Test
	fun test_ForecastDataIsVisible() {
		Thread.sleep(2000)
		onView(withId(R.id.forecast_recyclerview))
				.check(matches(isDisplayed()))
	}
	
	@Test
	fun test_IfActivityInView() {
		onView(withId(R.id.root_Layout))
				.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
	}
	
	@Test
	fun test_navigationToFavourites() {
	onView(withId(R.id.favourite_locations_fab))
				.perform(ViewActions.click())
		Thread.sleep(1000)
		ViewActions.pressBack()
	}
	
}
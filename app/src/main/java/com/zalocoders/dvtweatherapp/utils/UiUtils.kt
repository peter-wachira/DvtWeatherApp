package com.zalocoders.dvtweatherapp.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.dvtweatherapp.R

fun View.show() {
	visibility = View.VISIBLE
}

fun View.hide() {
	visibility = View.GONE
}

fun View.showSnackbar(message: String, length: Int) {
	val snackbar = Snackbar.make(this, message, length)
	
	snackbar.apply {
		setTextColor(ContextCompat.getColor(this.context, android.R.color.black))
		this.setBackgroundTint(ContextCompat.getColor(context, R.color.white))
		show()
	}
}

fun View.showRetrySnackBar(message: String, action: ((View) -> Unit)?) {
	val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
	
	snackbar.apply {
		this.setBackgroundTint(ContextCompat.getColor(this.context, android.R.color.holo_red_light))
		
		val colorWhite = ContextCompat.getColor(this.context, android.R.color.white)
		this.setTextColor(colorWhite)
		this.setActionTextColor(colorWhite)
		setAction("RETRY") {
			dismiss()
			action?.invoke(this@showRetrySnackBar)
		}
		show()
	}
}


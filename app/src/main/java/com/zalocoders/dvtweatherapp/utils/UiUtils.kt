package com.zalocoders.dvtweatherapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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
	val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
	
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

fun Context.getWeatherIcon(drawable:Int): BitmapDescriptor {
	val weatherIcon = ContextCompat.getDrawable(this,drawable) as BitmapDrawable
	
	
	val smallMarker = Bitmap.createScaledBitmap(weatherIcon.bitmap, 100, 100, false)
	
	val color = Color.BLUE
	
	val paint = Paint()
	val canvas = Canvas(smallMarker)
	
	paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
	canvas.drawBitmap(smallMarker, 0F, 0F, paint)
	return BitmapDescriptorFactory.fromBitmap(smallMarker)
	
}


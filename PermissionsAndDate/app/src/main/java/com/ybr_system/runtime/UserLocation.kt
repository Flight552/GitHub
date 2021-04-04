package com.ybr_system.runtime

import android.graphics.Color
import android.widget.ImageView
import org.threeten.bp.Instant

data class UserLocation(
    val id: Long = 0,
    val avatarColor: Int = Color.GREEN,
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val altitude: Double = 0.0,
    val speed: Float = 0f,
    var dateTime: Instant = Instant.now()
)

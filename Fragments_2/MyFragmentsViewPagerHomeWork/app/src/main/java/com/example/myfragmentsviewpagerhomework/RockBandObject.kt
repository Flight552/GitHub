package com.example.myfragmentsviewpagerhomework

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class RockBandObject(
    @StringRes val text: Int,
    @DrawableRes val image: Int,
    val genre: String = "Unknown"
)
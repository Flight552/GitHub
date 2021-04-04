package com.ybr_system.mylists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes view: Int, attachToRoot: Boolean = false) : View {
    return LayoutInflater.from(this.context)
        .inflate(view, this, attachToRoot)
}
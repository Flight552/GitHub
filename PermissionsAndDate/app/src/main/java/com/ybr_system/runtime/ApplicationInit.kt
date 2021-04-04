package com.ybr_system.runtime

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen


class ApplicationInit: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}
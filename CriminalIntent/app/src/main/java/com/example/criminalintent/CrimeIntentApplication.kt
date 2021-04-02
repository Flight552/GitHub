package com.example.criminalintent

import android.app.Application
import com.example.criminalintent.repository.CrimeRepository

class CrimeIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}
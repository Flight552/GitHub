package com.example.criminalintent.viewmodel

import androidx.lifecycle.ViewModel
import com.example.criminalintent.data.Crime

class CrimeListViewModel: ViewModel() {
    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.crimeName = "Crime #$i"
            crime.crimeStatus = i % 3 == 0
            crime.requiresPolice = i % 2 == 0
            crimes += crime
        }
    }
}
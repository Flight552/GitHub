package com.example.criminalintent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.criminalintent.data.Crime
import com.example.criminalintent.repository.CrimeRepository
import java.util.*

class CrimeListViewModel : ViewModel() {

    private val crimes = CrimeRepository.get()
    private val crimeIDLiveData = MutableLiveData<UUID>()

    val listCrimesLiveData: LiveData<List<Crime>> = crimes.getCrimes()

    var crimeLiveData: LiveData<Crime?> =
        Transformations.switchMap(crimeIDLiveData) {
            crimes.getCrimeById(it)
        }

    fun loadCrime(crimeID: UUID) {
        crimeIDLiveData.value = crimeID
    }

    fun saveCrime(crime: Crime) {
        crimes.addCrime(crime)
    }

    fun updateCrime(crime: Crime) {
        crimes.updateCrime(crime)
    }
}
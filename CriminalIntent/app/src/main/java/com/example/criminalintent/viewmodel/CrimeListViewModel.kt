package com.example.criminalintent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criminalintent.data.Crime
import com.example.criminalintent.repository.CrimeRepository
import kotlinx.coroutines.launch

class CrimeListViewModel : ViewModel() {

    private val crimes = CrimeRepository.get()

    val listCrimesLiveData: LiveData<List<Crime>> = crimes.getCrimes()
}
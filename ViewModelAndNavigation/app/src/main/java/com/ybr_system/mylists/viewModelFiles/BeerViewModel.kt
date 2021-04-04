package com.ybr_system.mylists.viewModelFiles

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ybr_system.mylists.dataFiles.SingleLiveEvent
import com.ybr_system.mylists.dataFiles.Beer


class BeerViewModel : ViewModel() {
    private val beerRepository = BeerRepository()
    private val beerViewModel = MutableLiveData<List<Beer>>(beerRepository.getBeerList())
    val beerList: LiveData<List<Beer>>
        get() = beerViewModel

    val showToastBeerLiveData =
        SingleLiveEvent<String>()

    fun showToastMessage(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun getBeerList(): List<Beer> {
        return beerRepository.getBeerList()
    }

    fun onClickBeerItem(position: Int): Long {
        return beerRepository.itemBeerID(position)
    }
    fun deleteBeerByPosition(position: Int) {
        beerRepository.deleteBeer(position)
        beerViewModel.postValue(beerRepository.getBeerList())
        showToastBeerLiveData.postValue("Deleted")
    }

    fun beerDialog(id: Long, name: String, country: String, type: String) {
        beerRepository.getBeerFromDialog(id, name, country, type)
        beerViewModel.postValue(beerRepository.getBeerList())
        showToastBeerLiveData.postValue("Added")
    }
}
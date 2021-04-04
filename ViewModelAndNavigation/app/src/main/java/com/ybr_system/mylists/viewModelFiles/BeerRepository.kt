package com.ybr_system.mylists.viewModelFiles

import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.dataFiles.BeerTypes
import com.ybr_system.mylists.dataFiles.beers
import com.ybr_system.mylists.imageLager

class BeerRepository {

    private var listOfBeers = beers

    fun itemBeerID(position: Int): Long {
        return when (val beerPosition = listOfBeers[position]) {
            is Beer.Cider -> beerPosition.id
            is Beer.Stout -> beerPosition.id
            is Beer.Dunkel -> beerPosition.id
            is Beer.Lager -> beerPosition.id
            is Beer.Porter -> beerPosition.id
            else -> 0L
        }
    }

    private fun addBeer(newBeer: Beer): Beer {
        listOfBeers = listOf(newBeer) + listOfBeers
        return newBeer
    }

    fun getBeerList(): List<Beer> {
        return listOfBeers
    }

    fun deleteBeer(position: Int) {
        val item = listOfBeers[position]
        listOfBeers = listOfBeers.filter {
            beer -> beer != item
        }
    }

    fun getBeerFromDialog(id: Long, name: String, country: String, type: String) {
        when (type) {
            BeerTypes.LAGER.name -> {
                addBeer(
                    Beer.Lager(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            BeerTypes.PORTER.name -> {
              addBeer(
                    Beer.Porter(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            BeerTypes.DUNKEL.name -> {
              addBeer(
                    Beer.Dunkel(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            BeerTypes.STOUT.name -> {
               addBeer(
                    Beer.Stout(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            BeerTypes.CIDER.name -> {
             addBeer(
                    Beer.Cider(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
        }
    }
}
package com.ybr_system.mylists.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ybr_system.mylists.R
import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.inflate

class StoutDelegateAdapter(private val onItemClick: (position: Int) -> Unit,
                           private val onItemDelete: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Beer.Stout, Beer, StoutDelegateAdapter.Stout>() {

    override fun isForViewType(item: Beer, items: MutableList<Beer>, position: Int): Boolean {
        return item is Beer.Stout
    }

    override fun onCreateViewHolder(parent: ViewGroup): Stout {
        return Stout(parent.inflate(R.layout.item_beer), onItemClick, onItemDelete)
    }

    override fun onBindViewHolder(item: Beer.Stout, holder: Stout, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Stout(
        view: View,
        onItemClick: (position: Int) -> Unit,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemClick, onItemDelete) {
        fun bind(beer: Beer.Stout) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

}
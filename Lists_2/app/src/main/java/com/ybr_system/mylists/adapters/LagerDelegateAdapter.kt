package com.ybr_system.mylists.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ybr_system.mylists.R
import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.inflate

class LagerDelegateAdapter(private val onItemDelete: (position: Int) -> Unit): AbsListItemAdapterDelegate<Beer.Lager, Beer, LagerDelegateAdapter.Lager>() {

    override fun isForViewType(item: Beer, items: MutableList<Beer>, position: Int): Boolean {
        return item is Beer.Lager
    }

    override fun onCreateViewHolder(parent: ViewGroup): Lager {
           return Lager(parent.inflate(R.layout.item_beer), onItemDelete)
    }

    override fun onBindViewHolder(item: Beer.Lager, holder: Lager, payloads: MutableList<Any>) {
        holder.bind(item)
    }


    class Lager(
        view: View, onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemDelete) {
        fun bind(beer: Beer.Lager) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

}
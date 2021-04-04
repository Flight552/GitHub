package com.ybr_system.mylists.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ybr_system.mylists.R
import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.inflate

class CiderDelegateAdapter(private val onItemDelete: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Beer.Cider, Beer, CiderDelegateAdapter.Cider>() {

    override fun isForViewType(item: Beer, items: MutableList<Beer>, position: Int): Boolean {
        return item is Beer.Cider
    }

    override fun onCreateViewHolder(parent: ViewGroup): Cider {
        return Cider(parent.inflate(R.layout.item_beer), onItemDelete)
    }

    override fun onBindViewHolder(item: Beer.Cider, holder: Cider, payloads: MutableList<Any>) {
        holder.bind(item)
    }
    class Cider(
        view: View,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemDelete) {
        fun bind(beer: Beer.Cider) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

}
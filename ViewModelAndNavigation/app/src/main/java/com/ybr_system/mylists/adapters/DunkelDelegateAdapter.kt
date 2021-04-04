package com.ybr_system.mylists.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ybr_system.mylists.R
import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.inflate

class DunkelDelegateAdapter(private val onItemClick: (position: Int) -> Unit,
                            private val onItemDelete: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Beer.Dunkel, Beer, DunkelDelegateAdapter.Dunkel>() {

    override fun isForViewType(item: Beer, items: MutableList<Beer>, position: Int): Boolean {
        return item is Beer.Dunkel
    }

    override fun onCreateViewHolder(parent: ViewGroup): Dunkel {
        return Dunkel(parent.inflate(R.layout.item_beer), onItemClick, onItemDelete)
    }

    override fun onBindViewHolder(item: Beer.Dunkel, holder: Dunkel, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Dunkel(
        view: View,
        onItemClick: (position: Int) -> Unit,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemClick, onItemDelete) {
        fun bind(beer: Beer.Dunkel) {
            bindBeerView(beer.image, beer.name, beer.type, beer.id.toString())
        }
    }

}
package com.ybr_system.mylists.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ybr_system.mylists.R
import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.inflate

class PorterDelegateAdapter(private val onItemClick: (position: Int) -> Unit,
                            private val onItemDelete: (position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Beer.Porter, Beer, PorterDelegateAdapter.Porter>() {

    override fun isForViewType(item: Beer, items: MutableList<Beer>, position: Int): Boolean {
        return item is Beer.Porter
    }

    override fun onCreateViewHolder(parent: ViewGroup): Porter {
        return Porter(parent.inflate(R.layout.item_beer), onItemClick, onItemDelete)
    }

    override fun onBindViewHolder(item: Beer.Porter, holder: Porter, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Porter(
        view: View,
        onItemClick: (position: Int) -> Unit,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemClick, onItemDelete) {
        fun bind(beer: Beer.Porter) {
            bindBeerView(beer.image, beer.name, beer.type, beer.id.toString())
        }
    }

}
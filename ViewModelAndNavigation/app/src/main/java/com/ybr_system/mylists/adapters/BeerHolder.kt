package com.ybr_system.mylists.adapters

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ybr_system.mylists.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_beer.*

abstract class BeerHolder(
    final override val containerView: View?,
    private val onItemClick: (position: Int) -> Unit,
    private val onItemDelete: (position: Int) -> Unit
) : RecyclerView.ViewHolder(containerView!!),
    LayoutContainer {
    init {
        containerView?.setOnClickListener {
            onItemClick(adapterPosition)
        }

        containerView?.setOnLongClickListener {
             onItemDelete(adapterPosition)
            return@setOnLongClickListener true
        }

    }

    protected fun bindBeerView(
        image: String,
        name: String,
        type: String,
        country: String
    ) {
        tv_beerName.text = name
        tv_beerCountry.text = country
        tv_beerType.text = type
        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.empty_beer_glass)
            .into(iv_BeerImage)
    }
}
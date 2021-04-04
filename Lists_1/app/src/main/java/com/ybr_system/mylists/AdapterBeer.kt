package com.ybr_system.mylists

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_beer.*
import kotlinx.android.synthetic.main.item_beer.view.*

class AdapterBeer(private val onItemDelete: (position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var beerList: List<Beer> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BEER_LAGER -> Lager(parent.inflate(R.layout.item_beer), onItemDelete)
            BEER_PORTER -> Porter(parent.inflate(R.layout.item_beer), onItemDelete)
            BEER_DUNKEL -> Dunkel(parent.inflate(R.layout.item_beer), onItemDelete)
            BEER_STOUT -> Stout(parent.inflate(R.layout.item_beer), onItemDelete)
            BEER_CIDER -> Cider(parent.inflate(R.layout.item_beer), onItemDelete)
            else -> error("Wrong type of beer")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (beerList[position]) {
            is Beer.Lager -> BEER_LAGER
            is Beer.Porter -> BEER_PORTER
            is Beer.Dunkel -> BEER_DUNKEL
            is Beer.Stout -> BEER_STOUT
            is Beer.Cider -> BEER_CIDER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Lager -> {
                val beer = beerList[position].let {
                    it as? Beer.Lager
                } ?: error("$position does not exists")
                holder.bind(beer)
            }
            is Porter -> {
                val beer = beerList[position].let {
                    it as? Beer.Porter
                } ?: error("$position does not exists")
                holder.bind(beer)
            }
            is Dunkel -> {
                val beer = beerList[position].let {
                    it as? Beer.Dunkel
                } ?: error("$position does not exists")
                holder.bind(beer)
            }
            is Stout -> {
                val beer = beerList[position].let {
                    it as? Beer.Stout
                } ?: error("$position does not exists")
                holder.bind(beer)
            }
            is Cider -> {
                val beer = beerList[position].let {
                    it as? Beer.Cider
                } ?: error("$position does not exists")
                holder.bind(beer)
            }
            else -> error("$holder - is wrong type")
        }
    }

    override fun getItemCount(): Int = beerList.size

    abstract class BeerHolder(
        final override val containerView: View?,
        private val onItemDelete: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView!!),
        LayoutContainer
  {
        init {
            containerView?.setOnClickListener {
                onItemDelete(adapterPosition)
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

    class Lager(
        view: View, onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemDelete) {
        fun bind(beer: Beer.Lager) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

    class Stout(
        view: View,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemDelete) {
        fun bind(beer: Beer.Stout) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

    class Dunkel(
        view: View,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemDelete) {
        fun bind(beer: Beer.Dunkel) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

    class Cider(
        view: View,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemDelete) {
        fun bind(beer: Beer.Cider) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

    class Porter(
        view: View,
        onItemDelete: (position: Int) -> Unit
    ) : BeerHolder(view, onItemDelete) {
        fun bind(beer: Beer.Porter) {
            bindBeerView(beer.image, beer.name, beer.type, beer.country)
        }
    }

    fun updateBeers(newBeer: List<Beer>) {
        beerList = newBeer
    }

    companion object {
        const val BEER_LAGER = 1
        const val BEER_PORTER = 2
        const val BEER_DUNKEL = 3
        const val BEER_STOUT = 4
        const val BEER_CIDER = 5
    }
}


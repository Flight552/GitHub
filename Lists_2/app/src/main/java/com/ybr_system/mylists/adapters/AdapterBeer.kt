package com.ybr_system.mylists.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.ListFragment
import com.ybr_system.mylists.R
import com.ybr_system.mylists.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_beer.*

class AdapterBeer(private val onItemDelete: (position: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // test to check diff
    private val differ = AsyncListDiffer<Beer>(this, BeerDiffUtil())

    private val delegatesManager = AdapterDelegatesManager<List<Beer>>()

    init {
        delegatesManager.addDelegate(CiderDelegateAdapter(onItemDelete))
            .addDelegate(PorterDelegateAdapter(onItemDelete))
            .addDelegate(StoutDelegateAdapter(onItemDelete))
            .addDelegate(LagerDelegateAdapter(onItemDelete))
            .addDelegate(DunkelDelegateAdapter(onItemDelete))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(differ.currentList, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(differ.currentList, position, holder)
    }

    override fun getItemCount(): Int = differ.currentList.size


    fun updateBeers(newBeer: List<Beer>) {
        differ.submitList(newBeer)
    }
    class BeerDiffUtil : DiffUtil.ItemCallback<Beer>() {

        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return when {
                (oldItem is Beer.Lager && newItem is Beer.Lager) -> oldItem.id == newItem.id
                (oldItem is Beer.Porter && newItem is Beer.Porter) -> oldItem.id == newItem.id
                (oldItem is Beer.Dunkel && newItem is Beer.Dunkel) -> oldItem.id == newItem.id
                (oldItem is Beer.Cider && newItem is Beer.Cider) -> oldItem.id == newItem.id
                (oldItem is Beer.Stout && newItem is Beer.Stout) -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }

    }
}


package com.example.criminalintent.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.data.inflate

class CrimeListAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<CrimeDelegate>(){


    private val differ = AsyncListDiffer<Crime>(this, CrimeDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeDelegate {
        return CrimeListViewHolder(parent.inflate(R.layout.list_item_crime), onItemClick)
    }

    override fun onBindViewHolder(holder: CrimeDelegate, position: Int) {
       val crime = differ.currentList[position]
        Log.d("CrimeListAdapter", "viewType - $crime")

        holder.bind(crime)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateCrimeList(crimes: List<Crime>) {
        differ.submitList(crimes)
    }

    class CrimeDiffUtil : DiffUtil.ItemCallback<Crime>() {
        override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem.crimeId == newItem.crimeId
        }

        override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
                return oldItem == newItem
        }

    }

}
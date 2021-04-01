package com.example.criminalintent.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.data.inflate
import kotlin.properties.Delegates

class CrimeListAdapter(private var list: List<Crime>) :
    RecyclerView.Adapter<CrimeDelegate>() {

    private lateinit var crime: Crime

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeDelegate {
        Log.d("CrimeListAdapter", "viewType - $viewType")

        return CrimeListViewHolder(parent.inflate(R.layout.list_item_crime))
    }

    override fun onBindViewHolder(holder: CrimeDelegate, position: Int) {
        crime = list[position]
        holder.bind(crime)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
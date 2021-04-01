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

        return when (viewType) {
            NO_POLICE -> {
                CrimeListViewHolder(
                    parent.inflate(R.layout.list_item_crime)
                )
            }
            CALL_POLICE -> {
                CrimeListPoliceViewHolder(
                    parent.inflate(R.layout.list_item_crime_requires_police)
                )
            }
            else -> {
                error("No Such Case")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        crime = list[position]
        val itemPosition = crime.requiresPolice
        return if (itemPosition) {
            CALL_POLICE
        } else {
            NO_POLICE
        }
    }


    override fun onBindViewHolder(holder: CrimeDelegate, position: Int) {
        crime = list[position]
        holder.bind(crime)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        private const val CALL_POLICE = 1
        private const val NO_POLICE = 0
    }
}
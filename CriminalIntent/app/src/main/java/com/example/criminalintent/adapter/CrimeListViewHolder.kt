package com.example.criminalintent.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import java.text.SimpleDateFormat
import java.util.*

class CrimeListViewHolder(view: View, private val onItemClick: (Int) -> Unit) : CrimeDelegate(view){
    private val nameView: TextView = itemView.findViewById(R.id.tvCrimeName)
    private val dateView: TextView = itemView.findViewById(R.id.tvCrimeDate)
    private val isSolved: ImageButton = itemView.findViewById(R.id.ibIsSolved)
    private var policeStatus: ImageButton = itemView.findViewById(R.id.ibPolice)

    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    override fun bind(crime: Crime) {
        nameView.text = crime.crimeName
        dateView.text = dateConverter(crime.crimeDate)

        isSolved.visibility = if(crime.crimeStatus) {
            View.VISIBLE
        } else {
            View.GONE
        }
        if(!crime.crimeStatus) {
            policeStatus.visibility = if (crime.requiresPolice) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun dateConverter(date: Date): String {
        val local = Locale.Builder().setLanguage("en").setRegion("RU").build()
        return SimpleDateFormat("EEEE, MMM d, yyyy", local).format(date)
    }

}
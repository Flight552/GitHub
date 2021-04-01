package com.example.criminalintent.adapter

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import kotlin.properties.Delegates

class CrimeListViewHolder(view: View) : CrimeDelegate(view){
    private val nameView: TextView = itemView.findViewById(R.id.tvCrimeName)
    private val dateView: TextView = itemView.findViewById(R.id.tvCrimeDate)
    private var police: Boolean = false

    init {
        itemView.setOnClickListener(this)
    }

    override fun bind(crime: Crime) {
        nameView.text = crime.crimeName
        dateView.text = crime.crimeDate.toString()
        police = crime.requiresPolice
    }

    override fun onClick(v: View?) {
        Toast.makeText(
            v?.context,
            "${nameView.text}" +
                    " No need for police",
            Toast.LENGTH_LONG
        ).show()
    }

}
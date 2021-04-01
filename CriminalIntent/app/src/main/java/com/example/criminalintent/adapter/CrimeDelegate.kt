package com.example.criminalintent.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.data.Crime

abstract class CrimeDelegate(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        open fun bind(crime: Crime) {}
}
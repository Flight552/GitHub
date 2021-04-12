package com.example.criminalintent.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*
import kotlin.collections.ArrayList

class TimePickerFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(TIME_RESULT) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

       val timeListener =  TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteInt ->
           val arrayListTime = arrayListOf<Int>(hourOfDay, minuteInt)
           Log.d("startTimeDialog", "${arrayListTime.size}")

           val args = Bundle().apply {
               putIntegerArrayList(TIME_RESULT_HOUR, arrayListTime)
           }
            setFragmentResult(TIME_RESULT_HOUR,  args)
        }

        return TimePickerDialog(
            requireContext(),
            timeListener,
            hour,
            minute,
            true
        )
    }
    
    companion object {
        const val TIME_RESULT_HOUR = "time_result_hour"
        const val TIME_RESULT = "time_result"
        fun newInstance(date: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(TIME_RESULT, date)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}
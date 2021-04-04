package com.example.myfragmentsviewpagerhomework

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RockAdapter(
    private val listSize: List<RockBandObject>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return listSize.size
    }

    override fun createFragment(position: Int): Fragment {
        val element: RockBandObject = listSize[position]
        return RockFragment.rockFragmentInstance(element.text, element.image)
    }
}
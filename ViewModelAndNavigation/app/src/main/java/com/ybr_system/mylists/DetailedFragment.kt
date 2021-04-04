package com.ybr_system.mylists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.detailed_beer_layout.*

class DetailedFragment: Fragment(R.layout.detailed_beer_layout) {

    private val args: DetailedFragmentArgs by navArgs()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setText()
    }

    private fun setText() {
        val text = "ID: ${args.id}"
        tv_detailedBeerID.text = text
    }
}
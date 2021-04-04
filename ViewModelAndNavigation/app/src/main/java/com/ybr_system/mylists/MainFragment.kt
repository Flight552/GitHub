package com.ybr_system.mylists

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ybr_system.mylists.dataFiles.LayoutStyle
import kotlinx.android.synthetic.main.main_fragment.*
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment(R.layout.main_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bt_LinearList.setOnClickListener {
            startBeerListFragment(LayoutStyle.LINEAR.name)
        }

        bt_GridList.setOnClickListener {
            startBeerListFragment(LayoutStyle.GRID.name)
        }

        bt_StaggeredGridList.setOnClickListener {
            startBeerListFragment(LayoutStyle.STAGGERED.name)
        }
    }

    private fun startBeerListFragment(layout: String) {
        val action = MainFragmentDirections
            .actionMainFragmentToListFragment(layout)
        findNavController()
            .navigate(action)
    }
}
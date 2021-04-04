package com.ybr_system.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MainFragmentTablet: Fragment(R.layout.main_fragment) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startFragmentList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun startFragmentList() {
        if (!hasChild(R.id.fragment_list)) {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_list, FragmentDescription())
                ?.commit()
        }

    }

    private fun hasChild(fragmentID: Int): Boolean {
        return childFragmentManager.findFragmentById(fragmentID) != null
    }

}
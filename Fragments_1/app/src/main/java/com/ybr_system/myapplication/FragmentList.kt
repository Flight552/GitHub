package com.ybr_system.myapplication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment

class FragmentList : Fragment(R.layout.fragment_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.let { it as ViewGroup }
            .children
            .mapNotNull { it as? Button }
            .forEach { button ->
                button.setOnClickListener {
                    startFromListFragment(button.text.toString())
                }
            }
    }

    private fun startFromListFragment(text: String) {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_list, FragmentDescription.getInstance(text))
                ?.addToBackStack("Fragment List")
                ?.commit()
    }
}
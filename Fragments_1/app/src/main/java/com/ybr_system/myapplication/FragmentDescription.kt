package com.ybr_system.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_description.*


class FragmentDescription : Fragment(R.layout.fragment_description) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_fragmentDescription.text = arguments?.getString(STATE_FRAGMENT_DESCRIPTION)
    }

    companion object {
        const val STATE_FRAGMENT_DESCRIPTION = "fragment description"
        fun getInstance(text: String): FragmentDescription {
            return FragmentDescription().createFragment {
                putString(STATE_FRAGMENT_DESCRIPTION, text)
            }
        }
    }
}
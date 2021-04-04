package com.example.myfragmentsviewpagerhomework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_enter.*

class ActivityEnter: Fragment(R.layout.activity_enter) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iv_rock.setImageResource(R.drawable.rock_hall)
        bt_welcome.setOnClickListener {
            startRockFragmentActivity()
        }
    }
    private fun startRockFragmentActivity() {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragment, ViewPagerFragment())
                ?.commit()
    }
}
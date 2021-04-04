package com.example.myfragmentsviewpagerhomework

import android.os.Bundle
import androidx.fragment.app.Fragment

fun <T: Fragment> T.createFragment(action: Bundle.() -> Unit): T {
    return apply {
        val args = Bundle().apply(action)
        arguments = args
    }
}

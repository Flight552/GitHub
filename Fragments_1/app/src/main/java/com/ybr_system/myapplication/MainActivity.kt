package com.ybr_system.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFragmentLogin()

    }

    private fun startFragmentLogin() {
        val isActive = supportFragmentManager.findFragmentById(R.id.frameLayout_main) != null
        if (!isActive) {
            onItemSelected(LoginFragment())
        }
    }

    override fun onItemSelected(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .replace(R.id.frameLayout_main, fragment)
            .commit()
    }

}

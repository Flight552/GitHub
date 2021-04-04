package com.ybr_system.mylists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFragment()
    }

    private fun startFragment() {
        val isFragment = supportFragmentManager.findFragmentById(R.id.fragment_MainActivity) != null
        if(!isFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_MainActivity, ListFragment())
                .commit()
        }
    }

}
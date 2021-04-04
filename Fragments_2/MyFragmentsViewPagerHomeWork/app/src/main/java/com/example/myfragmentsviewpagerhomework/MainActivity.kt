package com.example.myfragmentsviewpagerhomework

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startEnterFragment()

    }

    private fun startEnterFragment() {
        val hasFragment = supportFragmentManager.findFragmentById(R.id.mainFragment) != null
        if(!hasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainFragment, ActivityEnter())
                .commit()
        }
    }
}

fun getToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}
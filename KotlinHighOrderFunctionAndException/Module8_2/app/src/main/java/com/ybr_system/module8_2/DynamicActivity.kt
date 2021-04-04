package com.ybr_system.module8_2

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dynamic.*
import kotlin.random.Random

class DynamicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

        bt_Add.setOnClickListener {
            val addText = et_input.text.toString()
            val textViewToAdd = TextView(this).apply {
                text = addText
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = when (Random.nextInt(3)) {
                        0 -> Gravity.CENTER
                        1 -> Gravity.END
                        else -> Gravity.START
                    }
                }
            }
            c_dynamic.addView(textViewToAdd)
        }
    }
}
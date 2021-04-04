package com.ybr_system.module8_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dynamic.*
import kotlinx.android.synthetic.main.item_text.view.*

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

        bt_Add.setOnClickListener {
            val textToAdd = et_input.text.toString()
            val view = layoutInflater.inflate(R.layout.item_text, c_dynamic, false)
            view.apply {
                tv_itemText.text = textToAdd
                bt_Delete.setOnClickListener {
                    c_dynamic.removeView(this)
                }
            }
            c_dynamic.addView(view)
        }
    }
}
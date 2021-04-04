package com.ybr_system.module8_2

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Picasso.get().load("https://raw.githubusercontent.com/square/picasso/master/website/static/sample.png").into(iv_image)
        enterText()
        bt_button.setOnClickListener { tv_MainText.text = ""
            Toast.makeText(this, R.string.res_textIsClear, Toast.LENGTH_LONG).show()
        }
        bt_MakeOperation.setOnClickListener {
            makeLongOperation()
        }
    }

    private fun enterText() {
        et_EnterName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_MainText.text = s?.takeIf { it.isNotBlank() }?.let { name ->
                    resources.getString(R.string.et_hello, name)
                }
                bt_button.isEnabled = s?.isNotEmpty() ?: false
            }
        })
    }

    private fun makeLongOperation() {
            pb_LongOperation.visibility = View.VISIBLE
            bt_MakeOperation.isEnabled = false
        Handler().postDelayed({
            pb_LongOperation.visibility = View.GONE
            bt_MakeOperation.isEnabled = true
            Toast.makeText(this, "Long Operation Complete", Toast.LENGTH_SHORT).show()
        }, 5000)
    }
}


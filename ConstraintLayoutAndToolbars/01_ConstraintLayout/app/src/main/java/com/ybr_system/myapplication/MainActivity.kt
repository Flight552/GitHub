package com.ybr_system.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

const val urlImage = "https://cdn.pixabay.com/photo/2015/03/26/09/47/sky-690293_1280.jpg"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Glide.with(this).load(urlImage).into(iv_Image)

        cb_agreement.setOnClickListener {
            userInfoCheck(et_UserID.text.toString(), et_UserPassword.text.toString())
        }

        bt_EnterMain.setOnClickListener {
            if (userInfoCheck(et_UserID.text.toString(), et_UserPassword.text.toString()))
                progressAfterClick()
        }
    }

    private fun userInfoCheck(userEmail: String, userPassword: String): Boolean {
        var isChecked = false
        val getUserId =
            android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this, R.string.text_userInfo, Toast.LENGTH_SHORT).show()
        } else if (!getUserId) {
            Toast.makeText(this, R.string.toast_notValidEmail, Toast.LENGTH_SHORT).show()
        } else if (!cb_agreement.isChecked) {
            Toast.makeText(this, R.string.text_userAgreement, Toast.LENGTH_SHORT).show()
        } else {
            isChecked = true
        }
        if (!isChecked) {
            cb_agreement.isChecked = false
            bt_EnterMain.isClickable = false
        } else {
            bt_EnterMain.isEnabled = true
            bt_EnterMain.isClickable = true
        }

        return isChecked
    }

    private fun statusOnClick() {
        bt_EnterMain.isClickable = false
        et_UserID.isEnabled = false
        et_UserPassword.isEnabled = false
        cb_agreement.isClickable = false
        pb_Progress.visibility = View.VISIBLE
    }

    private fun progressAfterClick() {
        statusOnClick()
        Handler().postDelayed({
            bt_EnterMain.isClickable = true
            et_UserID.isEnabled = true
            et_UserPassword.isEnabled = true
            cb_agreement.isClickable = true
            pb_Progress.visibility = View.INVISIBLE
            Toast.makeText(this, (R.string.toast_LoginSuccessful), Toast.LENGTH_LONG).show()
        }, 2000)
    }

}



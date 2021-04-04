package com.ybr_system.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_enter.*

class EnterActivity : AppCompatActivity(R.layout.activity_enter) {
    val uri_kotlin = "https://www.udemy.com/course/kotlin_sumin/learn/lecture/15435822#overview"
    val uri_java = "https://www.udemy.com/course/android_sumin/learn/lecture/12895474#questions"
    private val phoneLauncher = prepareCall(ActivityResultContracts.Dial()) { isAnswered ->
        isAnswered ?: showToast("Call answered")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_MakeACall.setOnClickListener {
            startCalling()
        }

        bt_goDeepLink.setOnClickListener {
            val deep = DeepLinkActivity.goDeepUri(this, uri_kotlin)
            startActivity(deep)
        }
    }

    private fun startCalling() {
        val phoneNumber = et_PhoneNumber.text.toString()
        val isValidPhoneNumber = android.util.Patterns.PHONE.matcher(phoneNumber).matches()
        val phoneIntent = Intent(Intent.ACTION_DIAL)
        phoneIntent.resolveActivity(packageManager)?.also {
            if (isValidPhoneNumber) {
                phoneLauncher.launch(phoneNumber)
            } else {
                showToast("Wrong phone format")
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun enterSecondActivity(context: Context): Intent {
            return Intent(
                context, EnterActivity::class.java
            )
        }
    }
}
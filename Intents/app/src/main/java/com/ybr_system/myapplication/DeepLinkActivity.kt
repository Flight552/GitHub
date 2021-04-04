package com.ybr_system.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_deep.*

class DeepLinkActivity : AppCompatActivity(R.layout.activity_deep) {

    override fun onCreate(savedInstanceBundle: Bundle?) {
        super.onCreate(savedInstanceBundle)
        handleIntentData()
    }

    private fun handleIntentData() {
        val uriText = intent.getStringExtra(KEY_URI)
        tv_fullPath.text = uriText
        intent?.data?.lastPathSegment?.let {
            tv_lastPath.text = it
        }
    }

    companion object {
        private const val KEY_URI = "uri key"
        fun goDeepUri(context: Context, data: String): Intent {
            return Intent(context, DeepLinkActivity::class.java).putExtra(KEY_URI, data)
        }
    }
}
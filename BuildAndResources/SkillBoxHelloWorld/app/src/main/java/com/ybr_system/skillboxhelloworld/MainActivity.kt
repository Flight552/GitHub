package com.ybr_system.skillboxhelloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_free)
        val tv_Main = findViewById<TextView>(R.id.tv_Main)
        tv_Main.text = """
            VersionID=${BuildConfig.APPLICATION_ID}
            VersionName=${BuildConfig.VERSION_NAME}
            VersionType=${BuildConfig.BUILD_TYPE}
            VersionCode=${BuildConfig.VERSION_CODE}
            VersionFlavor=${BuildConfig.FLAVOR}
        """.trimIndent()
    }
}
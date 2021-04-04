package com.ybr_system.runtime

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class MainActivity : AppCompatActivity() {
    var dialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startMainFragment()
        checkGoogleServices()
    }

    private fun startMainFragment() {
        val isFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) != null
        if(!isFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment, PermissionFragmentInfo())
                .commit()
        }
    }

    companion object {
        private const val PERMISSION_FRAGMENT_TAG = "permission"
    }

    override fun onResume() {
        super.onResume()
        checkGoogleServices()
    }

    private fun checkGoogleServices() {
        val gApi = GoogleApiAvailability.getInstance()
        val resultCode = gApi.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                dialog = AlertDialog.Builder(this)
                    .setMessage("Your google play service is out of date. Please Update")
                    .setNeutralButton("OK", null)
                    .setCancelable(false)
                    .show()
            } else {
                dialog = AlertDialog.Builder(this)
                    .setMessage("please download the google play service")
                    .setNeutralButton("OK", null)
                    .setCancelable(false)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        dialog = null
    }
}
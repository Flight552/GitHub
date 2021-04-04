package com.ybr_system.runtime

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_permission_info.*
import org.threeten.bp.ZonedDateTime
import kotlin.random.Random
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit

class PermissionFragmentInfo : Fragment(R.layout.fragment_permission_info) {
    // Location Request Fields
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    // UserLocation constructor properties
    private var userLongitude: Double = 0.0
    private var userLatitude: Double = 0.0
    private var userAltitude: Double = 0.0
    private var userSpeed: Float = 0f

    // AlertDialog field
    private var permissionDialog: AlertDialog? = null

    // UserLocation Data Array
    private var list: List<UserLocation> = emptyList()

    //Flag to check the list is empty
    private var isSetDataEmpty = false

    // UserLocation Adapter
    private var userLocationAdapter: UserLocationList? = null

    // Date Time change dialogs
    private var dateDialog: Unit? = null
    private var timeDialog: Unit? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        // First adapter Initialization and empty fragment fields
        initList()
        setText()

        // Check Location Permissions
        if (checkPermission()) {
            if (!isSetDataEmpty) {
                setNoDataText()
            }
            getUserLocation()
        }
        //location request access
        locationRequestAccess()
        locationRequestBuilder()
        // Request access to location
        bt_requestAccess.setOnClickListener {
            sendRequest()
        }

        // Add new location to the list
        bt_requestLocation.setOnClickListener {
            getUserLocation()
            addUserLocation()
            if (!isSetDataEmpty) {
                setNoDataText()
            }
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    userLongitude = location.longitude
                    userLatitude = location.latitude
                    userAltitude = location.altitude
                    userSpeed = location.speed
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_ACCESS) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
            ) {
                tv_permissionTextDisplay.text = getString(R.string.text_permission_granted)
                bt_requestAccess.isGone = true
                bt_requestLocation.isVisible = true
                setNoDataText()
            } else if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_DENIED &&
                permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
            ) {
                val dialog = ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if (dialog) {
                    showDialog()
                }
            }
        }

    }

    private fun getUserLocation() {
        checkPermission()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    userLongitude = it.longitude
                    userLatitude = it.latitude
                    userAltitude = it.altitude
                    userSpeed = it.speed
                }
            }
            .addOnCanceledListener {
                Toast.makeText(requireContext(), "Запрос отменен", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                error("Could not get the location")
            }
    }

    private fun showDialog() {
        permissionDialog = AlertDialog.Builder(requireContext())
            .setMessage("Необходимо разрешение на использование местоположения")
            .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                sendRequest()
            })
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setNoDataText() {
        if (list.isEmpty()) {
            tv_EmptyLocation.isVisible = true
            rv_location.isGone = true
            isSetDataEmpty = false
        } else {
            tv_EmptyLocation.isGone = true
            rv_location.isVisible = true
            isSetDataEmpty = true
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun setText() {
        if (checkPermission()) {
            tv_permissionTextDisplay.text = getString(R.string.text_permission_granted)
            bt_requestAccess.isGone = true
            bt_requestLocation.isVisible = true
            tv_permissionTextDisplay.isGone = true
        } else {
            tv_permissionTextDisplay.text = getString(R.string.text_permission_denied)
            bt_requestAccess.isVisible = true
            bt_requestLocation.isGone = true
        }
    }

    private fun sendRequest() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_ACCESS
        )
    }

    private fun initList() {
        userLocationAdapter = UserLocationList { position -> addUserTimeData(position) }

        with(rv_location) {
            adapter = userLocationAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun addUserLocation() {
        val id = Random.nextLong()
        list = listOf(
            UserLocation(
                id = id,
                longitude = userLongitude,
                altitude = userAltitude,
                latitude = userLatitude,
                speed = userSpeed,
            )
        ) + list
        userLocationAdapter?.updateUserLocation(list)
        // не срабатывает скролл на 0 позицию
        rv_location.smoothScrollToPosition(0)
    }

    private fun addUserTimeData(position: Int) {
        val currentTime = LocalDateTime.now()
        var selectedDateTime: ZonedDateTime = ZonedDateTime.now()

        dateDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                timeDialog = TimePickerDialog(
                    requireContext(),
                    { _, hour, minute ->
                        selectedDateTime = LocalDateTime.of(year, month + 1, day, hour, minute)
                            .atZone(org.threeten.bp.ZoneId.systemDefault())
                        list[position].dateTime = selectedDateTime.toInstant()
                        userLocationAdapter?.notifyDataSetChanged()
                    },
                    currentTime.hour,
                    currentTime.minute,
                    false
                ).show()
            },
            currentTime.year,
            currentTime.month.value - 1,
            currentTime.dayOfMonth
        ).show()

    }

    private fun locationRequestAccess() {
        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun locationRequestBuilder() {
        val builder = LocationSettingsRequest.Builder()
        val client: SettingsClient = LocationServices.getSettingsClient(requireContext())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            Toast.makeText(requireContext(), "This is Builder", Toast.LENGTH_LONG).show()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                showDialog()
            }
        }

    }


    companion object {
        private const val REQUEST_CODE_ACCESS = 3131
    }

    override fun onResume() {
        super.onResume()
        if(!checkPermission()) startLocationUpdates()
    }


    private fun startLocationUpdates() {
        checkPermission()
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }



    override fun onDestroy() {
        super.onDestroy()
        permissionDialog?.dismiss()
        permissionDialog = null
        dateDialog = null
        timeDialog = null
    }
}
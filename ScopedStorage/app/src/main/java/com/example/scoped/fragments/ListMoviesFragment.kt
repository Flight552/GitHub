package com.example.scoped.fragments

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.app.RemoteAction
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoped.R
import com.example.scoped.data.Video
import com.example.scoped.data.VideoAdapter
import com.example.scoped.databinding.ListMoviesFragmentLayoutBinding
import com.example.scoped.repository.VideoRepository
import com.example.scoped.utils.ViewBindingFragment
import com.example.scoped.utils.autoCleared
import com.example.scoped.viewModel.ListViewModel
import kotlinx.coroutines.launch


class ListMoviesFragment : ViewBindingFragment<ListMoviesFragmentLayoutBinding>(ListMoviesFragmentLayoutBinding::inflate) {

    private var videoAdapter: VideoAdapter by autoCleared()
    private val videoListViewModel: ListViewModel by viewModels()
    private lateinit var requestPermissions: ActivityResultLauncher<Array<String>>
    private lateinit var requestLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        getList()
        onDeleteConfirmation()
        if (hasPermission().not()) {
            requestPermissions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionLauncher()
        initRecoverableLauncher()
    }
    override fun onResume() {
        super.onResume()
        videoListViewModel.updatePermissionState(hasPermission())
    }

    private fun initList() {
        videoAdapter = VideoAdapter(videoListViewModel::onDelete)
        with(binding.rvListMovies) {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun getList() {
        videoListViewModel.toastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_LONG).show()
        }
            videoListViewModel.videoLiveData.observe(viewLifecycleOwner) { list ->
                    videoAdapter.updateList(list)
            }
    }

    private fun onDeleteConfirmation() {
        videoListViewModel.recoverableActionLiveData.observe(viewLifecycleOwner) {
                handleUserAction(it)
        }
    }

    private fun handleUserAction(action: RemoteAction) {
        val request = IntentSenderRequest
            .Builder(action.actionIntent.intentSender)
            .build()
        requestLauncher.launch(request)
    }

    private fun initRecoverableLauncher() {
        requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK ) {
                videoListViewModel.confirmDelete()
            } else {
                videoListViewModel.declineDelete()
            }
        }
    }


    private fun requestPermissions() {
        requestPermissions.launch(
                *PERMISSIONS.toTypedArray()
        )
    }

    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                    requireContext(),
                    it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initPermissionLauncher() {
        requestPermissions = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionGrantedMap: Map<String, Boolean> ->
            if (permissionGrantedMap.values.all { it }) {
                videoListViewModel.permissionsGranted()
            } else {
                videoListViewModel.permissionsDenied()
            }
        }
    }


    companion object {
        private val PERMISSIONS = listOfNotNull(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE.takeIf {
                    Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                }
        )
    }
}
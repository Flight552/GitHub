package com.example.contentapp.downloadFile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.contentapp.R
import com.example.contentapp.databinding.FragmentDownloadFileLayoutBinding
<<<<<<< HEAD
import com.example.myfiles.repository.FileRepository
=======
import com.example.contentapp.downloadFile.repository.FileRepository
import kotlinx.coroutines.Dispatchers
>>>>>>> 7856411e004deb6f6a38baa10946f25d7da00a1f
import kotlinx.coroutines.launch


class FragmentActivity : Fragment(R.layout.fragment_download_file_layout) {

    private val repository: FileRepository by lazy<FileRepository> {
        FileRepository(requireContext())
    }

    private var _binding: FragmentDownloadFileLayoutBinding? = null
    private val binding
        get() = _binding!!

<<<<<<< HEAD
    private var link = ""
=======
    private var link = "https://gitlab.skillbox.ru/learning_materials/android_basic/-/raw/master/README.md"
>>>>>>> 7856411e004deb6f6a38baa10946f25d7da00a1f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = binding.btGetFile
        checkForFirstLaunch()
<<<<<<< HEAD
//        createLinkFromAssetFile()
        button.setOnClickListener {
            getUrlFromUser()
        }
    }

    private fun getUrlFromUser() {
        var userUrl = link
        userUrl = if(link.isNotEmpty()) {
=======
        button.setOnClickListener {
            getUrlFromUser()
        }
        binding.btShareFile.setOnClickListener {
            shareMyFile()
        }
    }

    private fun shareMyFile() {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.shareMyFile()
        }
    }

    private fun getUrlFromUser() {
        val userUrl = if(link.isNotEmpty()) {
>>>>>>> 7856411e004deb6f6a38baa10946f25d7da00a1f
            link
        } else {
            binding.etEnterURL.text.toString()
        }
        Log.d("FragmentActivity", "User Link $userUrl")
        if (!checkForFileExistence(repository, userUrl)) {
            lifecycleScope.launch {
                try {
                    if (userUrl.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Field should not be empty",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        setVisibility(false)
                        repository.getFile(userUrl)
                        setVisibility(true)
                    }
                } catch (t: Throwable) {

                }
            }
        } else {
            Toast.makeText(context, "File already downloaded", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun checkForFileExistence(repository: FileRepository, url: String): Boolean {
        return  repository.sharedPref?.contains(url) == true
    }

    private fun checkForFirstLaunch() {
            if(repository.isLaunchFirstTime() == true) {
                createLinkFromAssetFile()
                repository.setAfterFirstLaunch()
            }
    }

    private fun setVisibility(visible: Boolean) {
        binding.btGetFile.isEnabled = visible
        binding.etEnterURL.isEnabled = visible
        if (visible) {
            binding.progressBar.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun createLinkFromAssetFile() {
        try {
            link = resources.assets.open("links.txt")
                .bufferedReader()
                .use {
                    it.readText()
                }
        }catch (t: Throwable) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadFileLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
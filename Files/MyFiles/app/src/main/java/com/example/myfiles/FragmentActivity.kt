package com.example.myfiles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myfiles.databinding.FragmentLayoutBinding
import com.example.myfiles.repository.FileRepository
import kotlinx.coroutines.launch
import java.io.File


class FragmentActivity : Fragment(R.layout.fragment_layout) {

    private val repository: FileRepository by lazy<FileRepository> {
        FileRepository(requireContext())
    }

    private var _binding: FragmentLayoutBinding? = null
    private val binding
        get() = _binding!!

    private var link = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = binding.btGetFile
        checkForFirstLaunch()
//        createLinkFromAssetFile()
        button.setOnClickListener {
            getUrlFromUser()
        }
    }

    private fun getUrlFromUser() {
        var userUrl = link
        userUrl = if(link.isNotEmpty()) {
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
        _binding = FragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
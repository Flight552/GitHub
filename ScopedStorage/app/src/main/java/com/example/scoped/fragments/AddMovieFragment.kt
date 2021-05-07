package com.example.scoped.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.findNavController
import com.example.scoped.R
import com.example.scoped.databinding.AddMovieFragmentBinding
import com.example.scoped.utils.ViewBindingFragment
import com.example.scoped.viewModel.VideoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddMovieFragment : ViewBindingFragment<AddMovieFragmentBinding>(AddMovieFragmentBinding::inflate) {

    private val viewModel: VideoViewModel by viewModels()
    private var isEmptyFields: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findVideoErrorToast()
        binding.btSaveMovie.setOnClickListener {
            checkEditText()
            goBackToMainMenu()
            if (!isEmptyFields) {
                lifecycleScope.launch {
                    saveVideoFromUser()
                }
            } else {
                Toast.makeText(requireContext(), "Fields should not be empty", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun findVideoErrorToast() {
        viewModel.toastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
        }
    }

    private fun goBackToMainMenu(){
        viewModel.isMovie.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack(R.id.mainFragment, false)
            }
        }
    }

    private fun saveVideoFromUser() {
        val etMovieNumber = binding.etMovieNumber.text.toString()
        val etMovieTitle = binding.etMovieTitle.text.toString()
        lifecycleScope.launch {
            binding.btSaveMovie.isEnabled = false
            viewModel.saveVideo(etMovieNumber, etMovieTitle)
            binding.btSaveMovie.isEnabled = true
        }
    }

    private fun checkEditText() {
        val etMovieNumber = binding.etMovieNumber.text.toString()
        val etMovieTitle = binding.etMovieTitle.text.toString()
        isEmptyFields = viewModel.checkEditText(etMovieNumber, etMovieTitle)
    }
}
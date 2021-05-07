package com.example.scoped.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.scoped.databinding.FragmentMainBinding
import com.example.scoped.utils.AutoClearedValue
import com.example.scoped.utils.ViewBindingFragment
import com.example.scoped.viewModel.VideoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine


class MainFragment: ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: VideoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btListMovies.setOnClickListener {
            val directions = MainFragmentDirections.actionMainFragmentToListMoviesFragment()
            findNavController().navigate(directions)
        }

        binding.btAddMovie.setOnClickListener {
            val directions = MainFragmentDirections.actionMainFragmentToAddMovieFragment()
            findNavController().navigate(directions)
        }
    }
}
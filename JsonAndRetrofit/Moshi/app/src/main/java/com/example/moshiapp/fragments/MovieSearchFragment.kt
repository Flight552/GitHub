package com.example.moshiapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moshiapp.R
import com.example.moshiapp.databinding.MovieSearchFragmentBinding
import androidx.navigation.fragment.navArgs

class MovieSearchFragment: Fragment(R.layout.movie_search_fragment) {

    private var _binding: MovieSearchFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val button = binding.btSearch
        button.setOnClickListener {
            movieListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun movieListFragment() {
        val title = binding.etSearchMovie.text.toString()
        if(title.isNotEmpty()) {
          val action = MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieListFragment(title)
            findNavController().navigate(action)
        } else {
            showToast("Title should not be empty")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

}
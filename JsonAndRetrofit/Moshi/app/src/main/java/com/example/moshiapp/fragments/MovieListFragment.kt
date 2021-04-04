package com.example.moshiapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moshiapp.R
import com.example.moshiapp.databinding.MovieListFragmentBinding
import com.example.moshiapp.adapters.MovieAdapter
import com.example.moshiapp.viewModel.MovieViewModel
import com.skillbox.jsonandretrofit.utils.autoCleared

class MovieListFragment : Fragment(R.layout.movie_list_fragment) {

    private var _binding: MovieListFragmentBinding? = null
    private val binding
        get() = _binding!!

    private var movieAdapter: MovieAdapter by autoCleared()
    private val viewModel: MovieViewModel by viewModels()

    private val args: MovieListFragmentArgs by navArgs()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Log.d("TitleToAdd", "Args ${args.title}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        getMovieList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun initList() {
        val rv = binding.rvMovieList
        movieAdapter = MovieAdapter {
            position -> startDetailedMovieFragment(getItemPosition(position))
        }
        with(rv) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun startDetailedMovieFragment(id: String) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailed(id)
        findNavController().navigate(action)
    }

    private fun getItemPosition(position: Int): String {
        val movie = viewModel.movieList.value?.get(position)
        return movie?.imdbID.orEmpty()
    }

    private fun getMovieList() {
        val titleMovie = args.title
        viewModel.search(title = titleMovie)
        viewModel.isLoading.observe(viewLifecycleOwner) {

        }
        viewModel.movieList.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateList(movies)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
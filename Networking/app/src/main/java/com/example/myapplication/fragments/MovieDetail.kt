package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.Movie
import com.example.myapplication.data.MovieAdapter
import com.example.myapplication.databinding.MovieInformationLayoutBinding
import com.example.myapplication.viewmodel.MovieListViewModel
import com.example.viewmodeltest.autoCleared

class MovieDetail : Fragment(R.layout.movie_information_layout) {

    private val viewModel: MovieListViewModel by viewModels()
    private var movieAdapter: MovieAdapter by autoCleared()

    private val args: MovieDetailArgs by navArgs()

    private var _binding: MovieInformationLayoutBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieInformationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindByViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btRepeat = binding.btRepeat
        btRepeat.setOnClickListener {
            repeatConnectionOnError()
        }
    }

    private fun initList() {
        movieAdapter = MovieAdapter()
        with(binding.rvMovieList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        }
    }

    private fun bindByViewModel() {
        //arguments from search fragment
        val type: String = args.type
        val title: String = args.title
        val year: String = args.year
        viewModel.search(type, title, year)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showInvisibleView()
        }
        viewModel.movieList.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateList(movies)
        }
    }

    private fun showInvisibleView() {
        val button = binding.btRepeat
        val textView = binding.tvError
        if(viewModel.isError) {
            button.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE
        } else {
            button.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
        }
    }

    private fun repeatConnectionOnError() {
        initList()
        bindByViewModel()
    }
}
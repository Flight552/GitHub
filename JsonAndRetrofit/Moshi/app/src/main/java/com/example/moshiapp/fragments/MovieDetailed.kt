package com.example.moshiapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moshiapp.R
import com.example.moshiapp.databinding.MovieDetailedFragmentBinding
import com.example.moshiapp.movieData.MovieClass
import com.example.moshiapp.viewModel.MovieViewModel

class MovieDetailed : Fragment(R.layout.movie_detailed_fragment) {

    private var _binding: MovieDetailedFragmentBinding? = null
    private val binding: MovieDetailedFragmentBinding
        get() = _binding!!

    private val args: MovieDetailedArgs by navArgs()
    private val viewModel: MovieViewModel by viewModels()
    private var fullMovie: MovieClass? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btRateMe.setOnClickListener {
            addRating()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFullMovie()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun getFullMovie() {
        val idMovie = args.imdbId

        viewModel.search(id = idMovie)
        viewModel.isLoading.observe(viewLifecycleOwner) {

        }
        viewModel.movieList.observe(viewLifecycleOwner) { movies ->
            fullMovie = movies[0]
            addMovieDetails(fullMovie!!)
        }
    }

    // добавление рейтинга и парсинг в json
    private fun addRating() {
        val score = binding.etRating.text.toString().toInt()
        if(score > 10 || score < 0) {
            Toast.makeText(requireContext(), "The score should be within 0-10", Toast.LENGTH_LONG).show()
        } else {
            val message = "My Score: ${score.toString()}"
            binding.tvMovieScore.text = message
           val newMovie = fullMovie?.let {
                MovieClass(
                    imdbID = it.imdbID,
                    Title = it.Title,
                    Year = it.Year,
                    Genre = it.Genre,
                    Rated = it.Rated,
                    Ratings = mapOf("My Score" to score.toString()),
                    Poster = it.Poster
                )
            }
            viewModel.sendToJson(newMovie!!)
        }
    }

    private fun addMovieDetails(movie: MovieClass) {
        binding.tvID.text = movie.imdbID
        binding.tvMovieYear.text = movie.Year
        binding.tvMovieGenre.text = movie.Genre
        binding.tvMovieTitle.text = movie.Title
        binding.tvMovieScore.text = movie.Ratings.toString()
        binding.tvMovieRating.text = movie.Rated.toString()
        Glide.with(requireContext())
            .asDrawable()
            .load(movie.Poster)
            .into(binding.ivPosterMovie)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
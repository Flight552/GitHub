package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.Movie
import com.example.myapplication.databinding.MovieSearchLayoutBinding
import com.example.myapplication.viewmodel.MovieListViewModel

class MovieFragmentLayout : Fragment(R.layout.movie_search_layout) {

    private var myString: String = ""
    private var _binding: MovieSearchLayoutBinding? = null
    private val binding
        get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val textInput = binding.movieMenu
        val btSearch = binding.btSearchMovie

        // инициализация адаптера выбора типа фильма
        autoComplete()

        // переход на фрагмент адаптера фильма
        btSearch.setOnClickListener {
            startDetailedMovieFragment()
        }
    }

    // передача аргументов для поиска в адаптер фрагмента фильмов
    private fun startDetailedMovieFragment() {
        val etTitle: EditText = binding.etMovieTitle
        val etYear: EditText = binding.etMovieYear
        val title = etTitle.text.toString()
        val year = etYear.text.toString()
        if (title.isNotEmpty()) {
            val actions = MovieFragmentLayoutDirections.actionMovieFragmentLayoutToMovieDetail(
                myString,
                title,
                year,
            )
            findNavController().navigate(actions)
        } else {
            showToast("Title should not be empty")
        }

    }

    // установка спиннера для адаптера жанров фильма
    private fun setAutoAdapter(autoComplete: AutoCompleteTextView) {
        //обнуление переменной при обработке backstack
        myString = ""
        val array = resources.getStringArray(R.array.search_movie_choice)
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            array
        )
        autoComplete.setAdapter(adapter)
        autoComplete.threshold = 1
    }


    // инициализация spinner
    private fun autoComplete() {
        val autoComplete = binding.autoCompleteChoiceSearch
        setAutoAdapter(autoComplete)
        autoComplete.setOnItemClickListener { parent, _, position, _ ->
            myString = parent.getItemAtPosition(position).toString()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieSearchLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }
}
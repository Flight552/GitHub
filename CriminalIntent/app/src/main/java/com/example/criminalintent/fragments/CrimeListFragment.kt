package com.example.criminalintent.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.adapter.CrimeListAdapter
import com.example.criminalintent.data.Crime
import com.example.criminalintent.viewmodel.CrimeListViewModel
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.launch
import java.util.zip.Inflater


private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment(R.layout.fragment_crime_list) {

    private var _binding: FragmentCrimeListBinding? = null
    private val binding: FragmentCrimeListBinding
        get() = _binding!!


    private val crimeListViewModel: CrimeListViewModel by viewModels()
    private lateinit var crimeListRecycleView: RecyclerView
    private var adapter: CrimeListAdapter? = CrimeListAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        crimeListRecycleView = binding.rvCrimeList
        recycleViewInit()
        getListFromViewModel()
        return binding.root
    }

    private fun getListFromViewModel() {
        crimeListViewModel.listCrimesLiveData.observe(
            viewLifecycleOwner,
            { crimes ->
                crimes?.let {
                    Log.d("CrimeListFragment", "${crimes.size}")
                    setAdapter(crimes)
                }
            }
        )
    }

    private fun setAdapter(crimes: List<Crime>) {
        adapter = CrimeListAdapter(crimes)
        crimeListRecycleView.adapter = adapter
    }


    private fun recycleViewInit() {
        crimeListRecycleView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}
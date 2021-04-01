package com.example.criminalintent.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.databinding.CrimeFragmentBinding

class CrimeFragment() : Fragment(R.layout.crime_fragment) {

    private var _binding: CrimeFragmentBinding? = null
    private val binding: CrimeFragmentBinding
        get() = _binding!!

    private lateinit var crime: Crime


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    override fun onStart() {
        super.onStart()
        onTextChangeListener()
        onSetCheckBox()
    }

    private fun onTextChangeListener() {
        val textListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime.crimeName = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        }
        binding.etCrimeTitle.addTextChangedListener(textListener)
    }

    private fun onSetButtonText() {
        binding.btCrimeDetails.apply {
            text = crime.crimeDate.toString()
            isEnabled = false
        }
    }

    private fun onSetCheckBox() {
        binding.cbIsSolved.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.crimeStatus = isChecked
            }
        }
    }

    //-----------------------------------------------View Binding-----------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CrimeFragmentBinding.inflate(inflater, container, false)
        onSetButtonText()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
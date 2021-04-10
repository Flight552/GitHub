package com.example.criminalintent.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.*
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.databinding.CrimeFragmentBinding
import com.example.criminalintent.viewmodel.CrimeListViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"

class CrimeFragment : Fragment(R.layout.crime_fragment) {

    private var _binding: CrimeFragmentBinding? = null
    private val binding: CrimeFragmentBinding
        get() = _binding!!
    private val viewModel: CrimeListViewModel by viewModels()

    private lateinit var crimeUpdate: Crime


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crimeUpdate = Crime()
        val crimeID: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        viewModel.loadCrime(crimeID)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCrimeFromViewModel()
    }

    override fun onStart() {
        super.onStart()
        onSetCheckBox()
        startDateDialog()
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateCrime(crimeUpdate)
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


    private fun startDateDialog() {
        binding.btCrimeDetails.setOnClickListener {
            DatePickerFragment.newInstance(crimeUpdate.crimeDate).apply {
               this@CrimeFragment.setFragmentResultListener(DatePickerFragment.REQUEST_KEY) { key, bundle ->
                    val args = bundle.getSerializable(key) as Date
                   onDateSelected(args)
                }
                show(this@CrimeFragment.parentFragmentManager, DIALOG_DATE)
            }
        }
    }

    private fun getCrimeFromViewModel() {
        viewModel.crimeLiveData.observe(
            viewLifecycleOwner, { crime ->
                crime?.let { singleCrime ->
                    this.crimeUpdate = singleCrime
                    updateCrime()
                }
            }
        )
    }

    private fun updateCrime() {
        onSetButtonText()
        binding.tvCrimeTitle.text = crimeUpdate.crimeId.toString()
        binding.etCrimeTitle.setText(crimeUpdate.crimeName, TextView.BufferType.EDITABLE)
        binding.cbIsSolved.apply {
            isChecked = crimeUpdate.crimeStatus
            jumpDrawablesToCurrentState()
        }
        binding.cbCallPolice.apply {
            isChecked = !crimeUpdate.crimeStatus
            jumpDrawablesToCurrentState()
        }
    }

    private fun onSetButtonText() {
       val local = Locale.Builder().setLanguage("en").setRegion("RU").build()
        binding.btCrimeDetails.apply {
            text = SimpleDateFormat("EEEE, MMM d, yyyy", local).format(crimeUpdate.crimeDate)
        }
    }

    private fun onSetCheckBox() {
        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                crimeUpdate.crimeName = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        binding.etCrimeTitle.addTextChangedListener(titleWatcher)

        binding.cbIsSolved.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crimeUpdate.crimeStatus = isChecked
            }
        }
    }


    companion object {

        fun newInstance(crime: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crime)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }

    private fun onDateSelected(date: Date) {
        crimeUpdate.crimeDate = date
        updateCrime()
    }
}
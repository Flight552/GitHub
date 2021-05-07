package com.example.criminalintent.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.example.criminalintent.R
import com.example.criminalintent.data.Crime
import com.example.criminalintent.databinding.CrimeFragmentBinding
import com.example.criminalintent.viewmodel.CrimeListViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val DATE_FORMAT = "EEE, MMM, dd"
private const val REQUEST_CONTACT = 1

class CrimeFragment : androidx.fragment.app.Fragment(R.layout.crime_fragment) {

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
        val appComp = activity as AppCompatActivity
        val appBar = appComp.supportActionBar
        appBar?.title = "Report new crime"

    }

    override fun onStart() {
        super.onStart()
        onSetCheckBox()
        startDateDialog()
        sendReport()
        chooseSuspect()
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

    private fun sendReport() {
        binding.btCrimeReport.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getCrimeReport())
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.crime_report_subject)
                )
            }.also { intent ->
                val intentChooser = Intent.createChooser(intent, getString(R.string.send_report))
                startActivity(intentChooser)
            }
        }
    }


    private fun chooseSuspect() {
        binding.btCrimeSuspect.apply {
            val chooseContact = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            setOnClickListener {
                startActivityForResult(chooseContact, REQUEST_CONTACT)
            }
        }

    }

    private fun updateCrime() {
        onSetButtonText()
        binding.tvCrimeTitle.text = crimeUpdate.crimeId.toString()
        binding.etCrimeTitle.setText(crimeUpdate.crimeName, TextView.BufferType.EDITABLE)
        binding.cbIsSolved.apply {
            isChecked = crimeUpdate.crimeStatus
            jumpDrawablesToCurrentState()
        }
        if (crimeUpdate.crimeSuspect.isNotEmpty()) {
            binding.btCrimeSuspect.text = crimeUpdate.crimeSuspect
        }
    }

    private fun onSetButtonText() {
        val local = Locale.Builder().setLanguage("en").setRegion("RU").build()
        binding.btCrimeDetails.apply {
            text = SimpleDateFormat("EEEE, MMM d, yyyy", local).format(crimeUpdate.crimeDate)
        }

    }

    private fun getCrimeReport(): String {
        val solvedString = if (crimeUpdate.crimeStatus) {
            getString(R.string.crime_report_solved)
        } else {
            getString(R.string.crime_report_unsolved)
        }
        val dateString =
            android.text.format.DateFormat.format(DATE_FORMAT, crimeUpdate.crimeDate).toString()
        val suspect = if (crimeUpdate.crimeSuspect.isBlank()) {
            getString(R.string.crime_report_no_suspect)
        } else {
            getString(R.string.crime_report_suspect, crimeUpdate.crimeSuspect)
        }
        return getString(
            R.string.crime_report,
            crimeUpdate.crimeName,
            dateString,
            solvedString,
            suspect
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_CONTACT && data != null -> {
                val contactUri: Uri? = data.data
                val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                val cursor = requireActivity().contentResolver
                    .query(contactUri!!, queryFields, null, null, null)
                cursor?.use {
                    if (it.count == 0)
                        return

                    it.moveToFirst()
                    val suspect = it.getString(0)
                    crimeUpdate.crimeSuspect = suspect
                    viewModel.saveCrime(crimeUpdate)
                    binding.btCrimeSuspect.text = suspect
                }
            }
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
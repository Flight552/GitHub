package com.example.contentapp.contactSave

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.contentapp.R
import com.example.contentapp.databinding.ContactAddLayoutBinding
import com.example.contentapp.utils.hideKeyboardAndClearFocus
import kotlinx.android.synthetic.main.view_toolbar.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import java.util.jar.Manifest

class ContactSaveInfoFragment : Fragment(R.layout.contact_add_layout) {

    private var _binding: ContactAddLayoutBinding? = null
    private val binding: ContactAddLayoutBinding
        get() = _binding!!

    private val viewModel: ContactSaveViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        bindViewModel()
    }


    private fun saveContactWithPermission() {
        constructPermissionsRequest(
            android.Manifest.permission.WRITE_CONTACTS,
            onShowRationale = ::onContactPermissionShowRationale,
            onPermissionDenied = ::onContactPermissionDenied,
            onNeverAskAgain = ::onContactPermissionNeverAskAgain) {
                checkFields()
            }.launch()
    }

    private fun bindViewModel() {
        binding.btSaveContact.setOnClickListener {
            saveContactWithPermission()
        }
        viewModel.errorLiveEvent.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.successLiveData.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun checkFields() {
        val setName = binding.etEnterName.editText?.text?.toString()
        val setSurname = binding.etEnterSurname.editText?.text?.toString()
        val setPhone = binding.etEnterPhone.editText?.text?.toString()
        val setEmail = binding.etEnterEmail.editText?.text?.toString().orEmpty()
        Log.d("ContactSaveInfoFragment",  "Before When")
        Log.d("ContactSaveInfoFragment",  "$setName, $setSurname, $setPhone, $setEmail")
        when {
            setName.isNullOrEmpty() -> {
                setCheckToast("Field NAME should not be empty")
                Log.d("ContactSaveInfoFragment",  "EditTExt Name ${setName}")

            }
            setSurname.isNullOrEmpty() ->{
                setCheckToast("Field SURNAME should not be empty")
                Log.d("ContactSaveInfoFragment",  "EditTExt Name ${setSurname}")

            }
            setPhone.isNullOrEmpty() -> {
                setCheckToast("Field PHONE should not be empty")
                Log.d("ContactSaveInfoFragment",  "EditTExt Name ${setPhone}")

            }

            else -> {
                viewModel.save(
                    name = setName,
                    surname = setSurname,
                    phone = setPhone,
                    email = setEmail
                )
            }
        }
    }

    private fun initToolbar() {
        toolbar.title = "Add Contact to Address Book"
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactAddLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onContactPermissionDenied() {
        showToast(R.string.contact_add_permission_denied)
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        showToast(R.string.contact_add_permission_never_ask_again)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }

    private fun showToast(msg: Int) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun setCheckToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}
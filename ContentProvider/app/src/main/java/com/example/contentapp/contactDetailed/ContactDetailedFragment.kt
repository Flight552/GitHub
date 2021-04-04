package com.example.contentapp.contactDetailed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contentapp.R
import com.example.contentapp.databinding.ContactDetailedInfoBinding

class ContactDetailedFragment : Fragment(R.layout.contact_detailed_info) {

    private val viewModel: ContactDetailedViewModel by viewModels()

    private val args: ContactDetailedFragmentArgs by navArgs()

    private var _binding: ContactDetailedInfoBinding? = null
    private val binding: ContactDetailedInfoBinding
        get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInfo()
        deleteContact()
    }

    private fun deleteContact() {
        binding.btDeleteContact.setOnClickListener {
            val id = args.position
            viewModel.deleteSelectedContact(id)
            viewModel.successLiveData.observe(viewLifecycleOwner) {findNavController().popBackStack() }
        }
    }

    private fun setInfo() {
        val id = args.position
        viewModel.singleContact(id)
        viewModel.singleContactLiveData.observe(viewLifecycleOwner) {
            val fullName = "${it.name} ${it.surname}"
            binding.tvContactName.text = fullName
            binding.rvEmails.text = it.email.toString()
            binding.rvPhones.text = it.phone.toString()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactDetailedInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}
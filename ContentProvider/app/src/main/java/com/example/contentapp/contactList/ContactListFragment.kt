package com.example.contentapp.contactList


import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contentapp.BuildConfig
import com.example.contentapp.R
import com.example.contentapp.contactDetailed.ContactDetailedFragment
import com.example.contentapp.contactList.adapter.ContactListAdapter
import com.example.contentapp.data.Contact
import com.example.contentapp.databinding.FragmentContactsListBinding
import com.example.contentapp.utils.autoCleared
import com.example.contentapp.utils.toast
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class ContactListFragment : Fragment(R.layout.fragment_contacts_list) {

    private val TAG = "ContactListFragment"

    private var _binding: FragmentContactsListBinding? = null
    private val binding: FragmentContactsListBinding
        get() = _binding!!

    private val viewModel: ContactListViewModel by viewModels<ContactListViewModel>()
    private var adapterList: ContactListAdapter by autoCleared()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        createToolbar()
        initList()
        allLogs()
        binding.btFloating.setOnClickListener {
                findNavController().navigate(ContactListFragmentDirections.actionContactListFragmentToContactSaveInfoFragment())
            }

        binding.btDownloadFile.setOnClickListener {
            findNavController().navigate(ContactListFragmentDirections.actionContactListFragmentToFragmentActivity())
        }

        bindViewModel()

        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = { viewModel.loadList() }
            )
                .launch()
        }
    }

    private fun allLogs() {
        Log.d("CustomContentProvider", "AUTHORITIES = \"${BuildConfig.APPLICATION_ID}.provider\"")
    }

    private fun createToolbar() {
        binding.appBar.toolbar.title = "Contacts List"
    }

    private fun initList() {
        adapterList = ContactListAdapter {
            onItemClick(it)
        }

        with(binding.rvContacts) {
            adapter = adapterList
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {list ->
            Log.d(TAG, "from viewModel Observer ${list.toString()}")
            adapterList.items = list
        }
    }

    private fun onItemClick(contact: Contact) {
        findNavController().navigate(
            ContactListFragmentDirections
                .actionContactListFragmentToContactDetailedFragment(contact.id))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onContactPermissionDenied() {
        toast(R.string.contact_add_permission_denied)
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.contact_add_permission_never_ask_again)
    }
}
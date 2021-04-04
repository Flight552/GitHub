package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.R
import com.skillbox.github.adapter.RepositoryAdapter
import com.skillbox.github.adapter.UserAdapter
import com.skillbox.github.databinding.RepositoryListFragmentBinding
import com.skillbox.github.repository.RemoteRepository
import com.skillbox.github.repository.RemoteRepositoryPublicJSON
import com.skillbox.github.repository.RemoteUser
import com.skillbox.github.repository.UserViewModel
import com.skillbox.jsonandretrofit.utils.autoCleared
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryListFragment : Fragment(R.layout.repository_list_fragment) {

    private var _binding: RepositoryListFragmentBinding? = null
    private val binding: RepositoryListFragmentBinding
        get() = _binding!!

    private var userAdapter: RepositoryAdapter by autoCleared()
    private val viewModel: UserViewModel by viewModels()
    private var userID = -1L
    private var userList: List<RemoteRepositoryPublicJSON> = emptyList()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        coroutineScope.launch {
            getUserList()
        }

    }


    private fun initAdapter() {
        Log.d("RepoListFrag", "initadapter function")

        userAdapter = RepositoryAdapter { index ->
            Log.d("RepoListFrag", "position: $index")
            val id = userList[index].owner?.id
            if (id != null) {
                userID = id
            }
            openUserDetailedFragment(userID)
        }
        with(binding.rvUserList) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun getUserList() {
        viewModel.publicRepository()
        viewModel.data.observe(viewLifecycleOwner) {}
        viewModel.publicRepo.observe(viewLifecycleOwner) { users ->
            Log.d("RepoListFrag", "userRepo callback -> $users")
            userList = users
            userAdapter.updateList(userList)
        }
    }

    private fun openUserDetailedFragment(position: Long) {
        findNavController()
            .navigate(
                RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailedUser(
                    position
                )
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RepositoryListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
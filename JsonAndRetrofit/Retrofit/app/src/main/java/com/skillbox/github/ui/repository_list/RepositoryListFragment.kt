package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.R
import com.skillbox.github.adapter.UserAdapter
import com.skillbox.github.databinding.RepositoryListFragmentBinding
import com.skillbox.github.repository.RemoteUser
import com.skillbox.github.repository.UserViewModel
import com.skillbox.jsonandretrofit.utils.autoCleared

class RepositoryListFragment : Fragment(R.layout.repository_list_fragment) {

    private var _binding: RepositoryListFragmentBinding? = null
    private val binding: RepositoryListFragmentBinding
        get() = _binding!!

    private var userAdapter: UserAdapter by autoCleared()
    private val viewModel: UserViewModel by viewModels()
    private var userID = -1L
    private var userList: List<RemoteUser> = emptyList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
     //   openUserDetailedFragment(userID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        getUserList()
    }


    private fun initAdapter() {
        userAdapter = UserAdapter {index ->
            Log.d("RepoListFrag", "position: $index")
            val id = userList[index].id
            if(id != null) {
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
        viewModel.userList()
        viewModel.data.observe(viewLifecycleOwner) {}
        viewModel.user.observe(viewLifecycleOwner) { users ->
            userList = users
            userAdapter.updateList(users)
        }
    }

    private fun openUserDetailedFragment(position: Long) {
        findNavController()
            .navigate(RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailedUser(position))
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
package com.skillbox.github.ui.repository_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.databinding.RepositoryDetailedUserLayoutBinding
import com.skillbox.github.repository.RemoteUser
import com.skillbox.github.repository.UserViewModel

class RepositoryDetailedUser : Fragment(R.layout.repository_detailed_user_layout) {

    private val viewModel: UserViewModel by viewModels()
    private val args: RepositoryDetailedUserArgs by navArgs()

    private var _binding: RepositoryDetailedUserLayoutBinding? = null
    private val binding: RepositoryDetailedUserLayoutBinding
        get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUser()
    }

    private fun bindUser(user: RemoteUser) {
        binding.tvId.text = user.id.toString()
        binding.tvCompany.text = user.company
        binding.tvName.text = user.name
        Glide.with(requireContext())
            .asDrawable()
            .load(user.avatar)
            .into(binding.ivAvatar)
    }

    private fun initUser() {
        viewModel.userInfo(args.userID)
        viewModel.data.observe(viewLifecycleOwner) {}
        viewModel.singleUser.observe(viewLifecycleOwner) { user ->
            bindUser(user)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RepositoryDetailedUserLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
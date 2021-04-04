package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.data.Singleton
import com.skillbox.github.databinding.CurrentUserLayoutBinding
import com.skillbox.github.repository.RemoteUser
import com.skillbox.github.repository.UserViewModel

class CurrentUserFragment : Fragment(R.layout.current_user_layout) {

    private val viewModel: UserViewModel by viewModels()

    private var _binding: CurrentUserLayoutBinding? = null
    private val binding: CurrentUserLayoutBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUser()
    }

    private fun initUser() {
        viewModel.authenticatedUser()
        viewModel.data.observe(viewLifecycleOwner) {}
        viewModel.singleUser.observe(viewLifecycleOwner) { user ->
           // setUser(user)

            Glide.with(requireContext())
                .asDrawable()
                .load(user.avatar)
                .into(binding.ivAvatar)
            binding.tvName.text = user.name
            binding.tvId.text = user.id.toString()
            binding.tvCompany.text = user.company
            Log.d("Current_User_Fragment", "List Size - ${user.toString()}")
        }
    }

    private fun setUser(newUser: RemoteUser) {
        Glide.with(requireContext())
            .asDrawable()
            .load(newUser.avatar)
            .into(binding.ivAvatar)
        binding.tvName.text = newUser.name
        binding.tvId.text = newUser.id.toString()
        binding.tvCompany.text = newUser.company
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CurrentUserLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

}
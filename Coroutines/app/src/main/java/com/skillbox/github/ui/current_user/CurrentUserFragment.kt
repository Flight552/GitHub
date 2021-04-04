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
import com.skillbox.github.repository.*
import kotlinx.coroutines.*

class CurrentUserFragment : Fragment(R.layout.current_user_layout) {

    private val viewModel: UserViewModel by viewModels()
    private val job = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var _binding: CurrentUserLayoutBinding? = null
    private val binding: CurrentUserLayoutBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserAndHisFollowers()
    }


    private fun getUserAndHisFollowers() {
        job.launch {
            try {
                viewModel.authenticatedUser()
                viewModel.data.observe(viewLifecycleOwner) {}
                viewModel.customerUser.observe(viewLifecycleOwner) { user ->
                    printUserInfo(user)
                }
            } catch (t: Throwable) {
                Log.d("CurrentUserFragment", "$t")
            }
        }
    }


    private fun printUserInfo(user: AuthenticatedCustomUser) {
        val tvName = binding.tvName
        val ivAvatar = binding.ivAvatar
        val tvFollowers = binding.tvCompany
        tvName.text = user.user?.name
        tvFollowers.text = user.followers.toString()
        Glide.with(requireContext())
            .asDrawable()
            .load(user.user?.avatar)
            .into(ivAvatar)

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
package com.skillbox.github.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.github.R
import com.skillbox.github.repository.RemoteUser
import com.skillbox.github.utils.inflate

class UserAdapter(private val onItemSelected: (position: Int) -> Unit) : RecyclerView.Adapter<UserViewHolder>() {

   private val differ = AsyncListDiffer<RemoteUser>(this, object : DiffUtil.ItemCallback<RemoteUser>() {
        override fun areItemsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
            return oldItem == newItem
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent.inflate(R.layout.user_view)) {
            position -> onItemSelected(position)
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        Log.d("User_Adapter", "User List Size${differ.currentList.size}")
        return differ.currentList.size
    }

    fun updateList(users: List<RemoteUser>){
        Log.d("User_Adapter", "$users")
        differ.submitList(users)
    }
}

package com.skillbox.github.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.github.R
import com.skillbox.github.repository.RemoteRepository
import com.skillbox.github.repository.RemoteRepositoryPublicJSON
import com.skillbox.github.repository.RemoteUser
import com.skillbox.github.utils.inflate

class RepositoryAdapter(private val onItemSelected: (position: Int) -> Unit): RecyclerView.Adapter<RepositoryViewHolder>() {

    private val differ = AsyncListDiffer<RemoteRepositoryPublicJSON>(this, object: DiffUtil.ItemCallback<RemoteRepositoryPublicJSON>(){
        override fun areItemsTheSame(
            oldItem: RemoteRepositoryPublicJSON,
            newItem: RemoteRepositoryPublicJSON
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RemoteRepositoryPublicJSON,
            newItem: RemoteRepositoryPublicJSON
        ): Boolean {
            return oldItem == newItem
        }

    })
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent.inflate(R.layout.user_view)) {
            position -> onItemSelected(position)
        }
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    fun updateList(users: List<RemoteRepositoryPublicJSON>){
        Log.d("User_Adapter", "$users")
        differ.submitList(users)
    }
}
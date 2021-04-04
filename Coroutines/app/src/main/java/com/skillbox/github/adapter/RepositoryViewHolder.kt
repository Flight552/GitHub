package com.skillbox.github.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.repository.RemoteRepository
import com.skillbox.github.repository.RemoteRepositoryPublicJSON

class RepositoryViewHolder(view: View, onItemSelected: (position: Int) -> Unit): RecyclerView.ViewHolder(view) {
    private val tvID: TextView = view.findViewById(R.id.tv_id)
    private val tvName: TextView = view.findViewById(R.id.tv_name)
    private val tvLogin: TextView = view.findViewById(R.id.tv_login)
    private val ivAvatar: ImageView = view.findViewById(R.id.iv_avatar)

    init{
        view.setOnClickListener {
            onItemSelected(adapterPosition)
        }
    }

    fun bind(repo: RemoteRepositoryPublicJSON) {
        val userIDText = "User ID: ${repo.owner?.id}"
        val userNameText = "User name: ${repo.name}"
        val owner = "Owner: ${repo.owner?.login}"
        tvID.text = userIDText
        tvName.text = userNameText
        tvLogin.text = owner
        Glide.with(itemView)
            .asDrawable()
            .load(repo.owner?.avatar_url)
            .into(ivAvatar)
    }
}
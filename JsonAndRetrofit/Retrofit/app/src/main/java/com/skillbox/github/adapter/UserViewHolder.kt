package com.skillbox.github.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.repository.RemoteUser

class UserViewHolder(view: View, onItemSelected: (position: Int) -> Unit): RecyclerView.ViewHolder(view) {
   private val tvID: TextView = view.findViewById(R.id.tv_id)
   private val tvName: TextView = view.findViewById(R.id.tv_name)
   private val ivAvatar: ImageView = view.findViewById(R.id.iv_avatar)

   init{
       view.setOnClickListener {
           onItemSelected(adapterPosition)
       }
   }

    fun bind(user: RemoteUser) {
        val userIDText = "User ID: ${user.id}"
        val userNameText = "User name: ${user.username}"
        tvID.text = userIDText
        tvName.text = userNameText
        Glide.with(itemView)
            .asDrawable()
            .load(user.avatar)
            .into(ivAvatar)
    }
}
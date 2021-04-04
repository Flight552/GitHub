package com.example.contentapp.contactList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.contentapp.data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactListAdapter(
    onItemClick: (Contact) -> Unit
): AsyncListDifferDelegationAdapter<Contact>(ContactDiffCallback()) {

   init {
        delegatesManager.addDelegate(ContactDelegate(onItemClick))
    }

    class ContactDiffCallback(): DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }
}
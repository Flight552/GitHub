package com.example.contentapp.contactList.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contentapp.R
import com.example.contentapp.data.Contact
import com.example.contentapp.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactDelegate(
    private val onItemClick: (Contact)->Unit
): AbsListItemAdapterDelegate<Contact, Contact, ContactDelegate.Holder>() {

    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_contact), onItemClick)
    }

    override fun onBindViewHolder(item: Contact, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        onItemClick: (Contact) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var currentContact: Contact? = null

        init {
            containerView.setOnClickListener { currentContact?.let(onItemClick) }
        }

        fun bind(contact: Contact) {
            currentContact = contact
            val fullName = contact.name + " " + contact.surname
            containerView.tvContactName.text = fullName
            containerView.tvContactPhone.text = contact.id.toString()
            Log.d("ContactDelegate",  "Text for TextView ${fullName}")
        }

    }

}
package com.example.memtone

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.memtone.databinding.ItemContactBinding
import com.example.memtone.model.Contact

interface ContactActionListener {
   fun onContactClick(contact: Contact)

   fun onContactMoreClick(contact: Contact)
}

class ContactsAdapter(private val actionListener: ContactActionListener):
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(),
    View.OnClickListener {

    var contacts: List<Contact> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate( inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return ContactsViewHolder(binding)
    }

    override fun onClick(v : View) {
        val contact = v.tag as Contact
        when(v.id) {
            R.id.moreImageViewButton -> {
                 showPopupMenu(v)
            }
            else -> {
                actionListener.onContactClick(contact)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val contact = v.tag as Contact
        popupMenu.menu.add(0, 0, Menu.NONE, "Delete")

        popupMenu.setOnMenuItemClickListener {
            actionListener.onContactMoreClick(contact)
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
            holder.itemView.tag = contact
            moreImageViewButton.tag = contact

            textViewContact.text = contact.name
            textViewAddress.text = contact.address
        }
    }

    class ContactsViewHolder(
        val binding: ItemContactBinding
    ): RecyclerView.ViewHolder(binding.root)


}
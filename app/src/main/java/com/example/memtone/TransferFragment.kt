package com.example.memtone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memtone.databinding.FragmentTransferBinding
import com.example.memtone.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransferFragment : Fragment() {

    private var _binding : FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ContactsAdapter

    private val contactService: ContactService
        get() = (activity?.applicationContext as App).contactService

    private lateinit var contactDao: ContactDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)

       /* val contactDao = RVDatabase.getInstance(requireContext()).ContactDao()
        CoroutineScope(Dispatchers.Main).launch {
            contactDao.addContact(Contact(
                name = "SDFSF",
                address = "1231231"
            ))
            contactDao.getAllContact().forEach {
                binding.tvText.append("\n" + it.id)
            }
        }*/

        binding.btnSend.setOnClickListener {
            findNavController().navigate(R.id.action_transferFragment_to_zaebokFragment)
        }

        binding.btnFabAddContact.setOnClickListener {
            BottomSheetTransferFragment().show(fragmentManager!!, "newContactTag")
        }

        adapter = ContactsAdapter(object : ContactActionListener{
            override fun onContactClick(contact: Contact) {
                findNavController().navigate(R.id.action_transferFragment_to_zaebokFragment)
               Toast.makeText(context, "TO ${contact.address}", Toast.LENGTH_SHORT).show()
            }

            override fun onContactMoreClick(contact: Contact) {
                contactService.deleteContact(contact)
            }
        })

        val layoutManager = LinearLayoutManager(context)
        adapter.contacts = contactService.getContacts()
        binding.recyclerViewContacts.layoutManager = layoutManager
        binding.recyclerViewContacts.adapter = adapter

        contactService.addListener(contactsListener)

        binding.recyclerViewContacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 10 && binding.btnFabAddContact.isShown) {
                    binding.btnFabAddContact.hide()
                }

                if (dy < -10 && !binding.btnFabAddContact.isShown) {
                    binding.btnFabAddContact.show()
                }

                if (!recyclerView.canScrollVertically(-1)) {
                    binding.btnFabAddContact.show()
                }
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        contactService.removeListener(contactsListener)
    }

    private val contactsListener : ContactsListener = {
        adapter.contacts = it
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_profile).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
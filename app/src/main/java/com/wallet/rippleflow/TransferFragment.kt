package com.wallet.rippleflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wallet.rippleflow.databinding.FragmentTransferBinding
import com.wallet.rippleflow.contact.*
import com.wallet.rippleflow.contact.Adapter.ContactsAdapter
import com.wallet.rippleflow.contact.Model.Contact
import com.wallet.rippleflow.contact.ViewModel.ContactViewModel

class TransferFragment : Fragment() {

    private var _binding : FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)

        binding.btnSend.setOnClickListener {
            findNavController().navigate(R.id.action_transferFragment_to_zaebokFragment)
        }

        binding.btnFabAddContact.setOnClickListener {
            BottomSheetTransferFragment().show(fragmentManager!!, "newContactTag")
        }

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

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.getAllContactData(requireContext()).observe(viewLifecycleOwner, Observer {
            contactsAdapter.setData(it as ArrayList<Contact>)
        })

        contactsAdapter = ContactsAdapter(
            requireContext(),
            object : ContactActionListener{
                override fun onContactClick(contact: Contact) {
                    findNavController().navigate(R.id.action_transferFragment_to_zaebokFragment)
                }

                override fun onContactMoreClick(contact: Contact) {
                    contactViewModel.deleteContact(requireContext(), contact)
                }

            },
            contacts = ArrayList<Contact>()
            )

        binding.recyclerViewContacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactsAdapter
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
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
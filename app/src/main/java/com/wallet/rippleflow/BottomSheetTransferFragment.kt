package com.wallet.rippleflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wallet.rippleflow.databinding.FragmentBottomSheetTransferBinding
import com.wallet.rippleflow.contact.Model.Contact
import com.wallet.rippleflow.contact.ViewModel.ContactViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTransferFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetTransferBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding.btnSaveContact.setOnClickListener {
            saveContact()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetTransferBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun saveContact() {
        val name = binding.editTextName.text.toString()
        val address = binding.editTextAddress.text.toString()
        //TODO
        binding.editTextName.setText("")
        binding.editTextAddress.setText("")
        val contact = Contact(
            id = 0,
            name = name,
            address = address
        )

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.insert(requireContext(), contact)

        println(contact)

        dismiss()
    }


}
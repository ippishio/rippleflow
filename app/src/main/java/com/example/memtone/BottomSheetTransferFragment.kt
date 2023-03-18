package com.example.memtone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.memtone.databinding.FragmentBottomSheetTransferBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTransferFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetTransferBinding

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
        Toast.makeText(activity, name, Toast.LENGTH_SHORT).show()
        dismiss()
    }
}
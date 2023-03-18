package com.example.memtone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memtone.databinding.FragmentBottomSheetChangePinBinding
import com.example.memtone.databinding.FragmentBottomSheetTransferBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetChangePINFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetChangePinBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        binding.btnChangePin.setOnClickListener {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetChangePinBinding.inflate(inflater, container, false)

        return binding.root
    }


}
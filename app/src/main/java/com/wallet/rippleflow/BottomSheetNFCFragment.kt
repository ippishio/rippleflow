package com.wallet.rippleflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.wallet.rippleflow.databinding.FragmentBottomSheetNfcBinding
import com.wallet.rippleflow.databinding.FragmentBottomSheetTransferBinding
import com.wallet.rippleflow.databinding.FragmentNfcBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetNFCFragment: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetNfcBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        println("SDFSDF")
//        binding.btnGO.setOnClickListener {
//            pushNFC()
//        }
        //TODO Like something connecting with nfc
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetNfcBinding.inflate(inflater, container, false)

        return binding.root
    }

 /*   fun pushNFC() {
        findNavController().navigate(R.id.action_nfcFragment_to_zaebokFragment)
        Thread.sleep(3000)
//        Thread.sleep(3000)
        dismiss()
    }*/
}
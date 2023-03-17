package com.example.memtone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentNfcBinding


class NfcFragment : Fragment() {

    private var _binding : FragmentNfcBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNfcBinding.inflate(inflater, container, false)

        binding.btnGO.setOnClickListener {
            BottomSheetNFCFragment().show(fragmentManager!!, "NFCtag")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
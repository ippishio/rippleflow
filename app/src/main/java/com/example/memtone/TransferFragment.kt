package com.example.memtone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentTransferBinding

class TransferFragment : Fragment() {

    private var _binding : FragmentTransferBinding? = null
    private val binding get() = _binding!!

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
        setHasOptionsMenu(true)
        return binding.root
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
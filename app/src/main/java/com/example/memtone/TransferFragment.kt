package com.example.memtone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentTransferBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

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
            BottomSheetFragment().show(fragmentManager!!, "newContactTag")
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.memtone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentPinCodeBinding


class PinCodeFragment : Fragment() {

    private var _binding : FragmentPinCodeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentPinCodeBinding.inflate(inflater, container, false)

        binding.textButtonNext.setOnClickListener {
//            findNavController().navigate(R.id.action_pinCodeFragment2_to_mainFragment2)
//            vagetFragmentNavController(R.id.actio)
//            navigationController.navigate(R.id.action_pinCodeFragment2_to_mainFragment2)
            findNavController().navigate(R.id.action_pinCodeFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.memtone

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        /*binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_transferFragment)
        }*/

        /*binding.btnNFC.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nfcFragment)
        }*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        

        binding.btnNFC.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_nfcFragment)
        }

        binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_transferFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
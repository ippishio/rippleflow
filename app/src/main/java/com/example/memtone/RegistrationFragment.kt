package com.example.memtone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentPinCodeBinding
import com.example.memtone.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {


    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)


        binding.btnLogToMain.setOnClickListener {

            val addressText = binding.editTextAddress.text.toString()
            val passwordText = binding.editTextPassword.text.toString()

            findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
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
import com.example.memtone.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {


    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!


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
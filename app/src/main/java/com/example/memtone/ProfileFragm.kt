package com.example.memtone

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.memtone.databinding.FragmentProfile2Binding


class ProfileFragm : Fragment() {

    private var _binding : FragmentProfile2Binding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfile2Binding.inflate(inflater, container, false)
        preferences = activity?.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)!!

        binding.btnChangePIN.setOnClickListener {
            BottomSheetChangePINFragment().show(fragmentManager!!, "SS")
        }

        binding.btnLogOut.setOnClickListener{
            preferences.edit()
                .putString(APP_PREFERENCES_KEY, "")
                .apply()
            findNavController().navigate(R.id.action_profileFragm_to_registrationFragment)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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